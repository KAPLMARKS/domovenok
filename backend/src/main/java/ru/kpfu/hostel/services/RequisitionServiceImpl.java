package ru.kpfu.hostel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.RequisitionDto;
import ru.kpfu.hostel.exception.EmailAlreadyConfirmedException;
import ru.kpfu.hostel.exception.TokenExpiredException;
import ru.kpfu.hostel.models.Employee;
import ru.kpfu.hostel.models.Requisition;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.VerificationToken;
import ru.kpfu.hostel.repositories.RequisitionRepository;
import ru.kpfu.hostel.services.email.EmailService;

import java.util.UUID;

@Service
public class RequisitionServiceImpl implements RequisitionService {
    private final RequisitionRepository requisitionRepository;
    private final VerificationTokenService verificationTokenService;
    private final UniversityService universityService;
    private final EmployeeService employeeService;
    private final EmailService emailService;

    @Autowired
    public RequisitionServiceImpl(RequisitionRepository requisitionRepository,
                                  VerificationTokenService verificationTokenService,
                                  UniversityService universityService,
                                  EmployeeService employeeService,
                                  EmailService emailService) {
        this.requisitionRepository = requisitionRepository;
        this.verificationTokenService = verificationTokenService;
        this.universityService = universityService;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }


    @Override
    public void createRequisition(RequisitionDto.Request.CreateRequisition requisitionDto) {
        Requisition requisition = Requisition.builder()
                .email(requisitionDto.getEmail())
                .isAccepted(false)
                .organizationName(requisitionDto.getOrganizationName())
                .build();
        requisitionRepository.save(requisition);

        VerificationToken verificationToken = verificationTokenService.generateToken(requisition);

        emailService.sendVerifyEmail(requisition.getEmail(), verificationToken.getToken());
    }

    @Override
    public void confirmRequisition(String verifyToken) {
        VerificationToken verificationToken = verificationTokenService.findVerificationToken(verifyToken);

        if (!verificationTokenService.isVerifyToken(verificationToken)) {
            throw new TokenExpiredException();
        }

        Requisition requisition = verificationToken.getRequisition();
        if (requisition.isAccepted()) {
            throw new EmailAlreadyConfirmedException();
        }
        requisition.setAccepted(true);
        requisitionRepository.save(requisition);

        University university = universityService.createUniversity(requisition.getOrganizationName());
        String randomPassword = UUID.randomUUID().toString();
        Employee employee = employeeService.createAdmin(requisition.getEmail(), randomPassword, university);

        emailService.sendDataAfterVerifyEmail(employee.getEmail(), randomPassword, university.getKey());
    }
}
