package ru.kpfu.hostel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.dto.ClaimDto;
import ru.kpfu.hostel.models.*;
import ru.kpfu.hostel.models.claim.AnswerType;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.claim.Type;
import ru.kpfu.hostel.models.student.FormEducation;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.models.student.StudentCategory;
import ru.kpfu.hostel.repositories.*;
import ru.kpfu.hostel.services.email.EmailService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {
    private final ClaimRepository claimRepository;
    private final PlaceRepository placeRepository;
    private final ExcelParsingStudentService excelParsingStudentService;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final EmailService emailService;
    private final BallsRepository ballsRepository;

    @Override
    public Claim createClaim(ClaimDto.Response.CreateClaim createClaim, UserModel declarer, Long placeId) {
        Balls balls = ballsRepository.findByUniversity(declarer.getUniversity());
        Student student = (Student) declarer;
        Place place = placeRepository.findById(placeId).orElseThrow();
        Claim claim = Claim.builder()
                .type(createClaim.getType())
                .declarer(declarer)
                .message(createClaim.getMessage())
                .answerType(AnswerType.WAIT)
                .status(1)
                .place(place)
                .build();
        student.setRating(excelParsingStudentService.countRating((Student) declarer, balls));
        student.setNowStatement(true);
        studentRepository.save(student);
        return claimRepository.save(claim);
    }

//    @Override
//    public Claim createClaimWithDoc(String message, Type type, String docUrl, UserModel declarer) {
//        Claim claim = Claim.builder()
//                .type(type)
//                .declarer(declarer)
//                .message(message)
//                .docUrl(docUrl)
//                .answerType(AnswerType.WAIT)
//                .build();
//        return claimRepository.save(claim);
//    }


    @Override
    public List<Claim> getClaims(UserModel userModel) {
        return claimRepository.findAllByDeclarerUniversity(userModel.getUniversity());
    }

    @Override
    public List<Claim> getClaimsByStudent(UserModel userModel) {
        return claimRepository.findAllByDeclarer(userModel);
    }

    @Override
    public List<Claim> geyClaimsByAnswerType(AnswerType answerType, UserModel userModel) {
        return claimRepository.findAllByAnswerTypeAndDeclarerUniversity(answerType, userModel.getUniversity());
    }

    @Override
    public Claim successClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        claim.setAnswerType(AnswerType.SUCCESS);
        if(claim.getType().equals(Type.WISH)) {
            studentService.addPlace((Student)claim.getDeclarer(), claim.getPlace());
            emailService.sendSuccessNotification(claim.getDeclarer().getEmail());
        } else if (claim.getType().equals(Type.RELOCATION)) {
            studentService.editPlace((Student) claim.getDeclarer(), claim.getPlace());
            emailService.sendNotification(claim.getDeclarer().getEmail(), claim.getPlace());
        } else if(claim.getType().equals(Type.EVICTION)) {
            studentService.removePlace((Student) claim.getDeclarer());
            emailService.sendSuccessNotification(claim.getDeclarer().getEmail());
        }
        return claimRepository.save(claim);
    }

    @Override
    public Claim declinedClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        claim.setAnswerType(AnswerType.DECLINED);
        emailService.sendDeclinedNotification(claim.getDeclarer().getEmail());
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getClaimsByHostel(Hostel hostel) {
        return claimRepository.findAllByPlace_Room_Hostel(hostel);
    }

    @Override
    public List<Claim> getClaimsByTypeAndHostel(Type type, Hostel hostel) {
        return claimRepository.findAllByPlace_Room_HostelAndTypeAndStatusAndAnswerType(hostel, type, 1, AnswerType.WAIT);
    }
    @Override
    public List<Claim> getClaimsByStudGorodok(AnswerType answerType, UserModel userModel) {
        return claimRepository.findAllByAnswerTypeAndDeclarerUniversityAndStatus(answerType, userModel.getUniversity(), 2);
    }

    @Override
    public Claim editClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        claim.setStatus(2);
        if(claim.getType().equals(Type.PROBLEM)) {
            claim.setAnswerType(AnswerType.SUCCESS);
        }
        return  claimRepository.save(claim);
    }
}
