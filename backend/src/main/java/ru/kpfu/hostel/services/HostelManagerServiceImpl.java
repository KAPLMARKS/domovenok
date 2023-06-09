package ru.kpfu.hostel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.HostelManager;
import ru.kpfu.hostel.models.University;

import ru.kpfu.hostel.repositories.HostelManagerRepository;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HostelManagerServiceImpl implements HostelManagerService {

    private final HostelManagerRepository hostelManagerRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("forEmployee")
    private final ExcelParsingHostelManagerService excelParsingHostelManagerService;
    private final UniversityService universityService;
    private final HostelService hostelService;

    @Autowired
    public HostelManagerServiceImpl(HostelManagerRepository hostelManagerRepository, PasswordEncoder passwordEncoder,
                                    ExcelParsingHostelManagerService excelParsingHostelManagerService, UniversityService universityService, HostelService hostelService) {
        this.hostelManagerRepository = hostelManagerRepository;
        this.passwordEncoder = passwordEncoder;
        this.excelParsingHostelManagerService = excelParsingHostelManagerService;
        this.universityService = universityService;
        this.hostelService = hostelService;

    }
    @Override
    public HostelManager loadHostelManager(EmployeeDto.Request.EmployeeLoad hostelManager, University university, Hostel hostel) {
        return hostelManagerRepository.save(HostelManager.builder()
                .dolgnost(hostelManager.getDolgnost())
                .email(hostelManager.getEmail())
                .lastName(hostelManager.getLastName())
                .middleName(hostelManager.getMiddleName())
                .firstName(hostelManager.getFirstName())
                .roles(Stream.of(hostelManager.getRoles()).collect(Collectors.toSet()))
                .university(university)
                .password(passwordEncoder.encode(hostelManager.getPassword()))
                .hostel(hostel)
                .build()
        );
    }

    @Override
    public List<HostelManager> loadHostelManagerExcel(InputStream inputStream, University university, Hostel hostel) {
        return hostelManagerRepository.saveAll(excelParsingHostelManagerService.parseHostelManager(inputStream, university, hostel));
    }
}
