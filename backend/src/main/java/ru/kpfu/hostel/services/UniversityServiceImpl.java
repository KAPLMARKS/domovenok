package ru.kpfu.hostel.services;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.BallsDto;
import ru.kpfu.hostel.dto.UniversityDto;
import ru.kpfu.hostel.exception.NotFoundElementException;
import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.repositories.BallsRepository;
import ru.kpfu.hostel.repositories.UniversityRepository;

import java.util.UUID;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final BallsRepository ballsRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository, BallsRepository ballsRepository) {
        this.universityRepository = universityRepository;
        this.ballsRepository = ballsRepository;
    }

    @Override
    public University findByKey(String universityKey) {
        return universityRepository.findByKey(universityKey).orElseThrow(NotFoundElementException::new);
    }

    @Override
    public University createUniversity(String organizationName) {
        University university = University.builder()
                .organizationName(organizationName)
                .key(UUID.randomUUID().toString())
                .build();
        universityRepository.save(university);
        return university;
    }

    @Override
    public UniversityDto.Response.SecretKey refreshKey(University university) {
        university.setKey(UUID.randomUUID().toString());
        return new UniversityDto.Response.SecretKey(universityRepository.save(university).getKey());
    }

    @Override
    public UniversityDto.Request.SecretKey getKey(University university) {
        return new UniversityDto.Request.SecretKey(university.getKey());
    }

    @Override
    public Balls editBalls(BallsDto.Request.BallsData b, University university) {
        Balls bal = ballsRepository.findByUniversity(university);
        if(bal == null) {
            Balls balls = Balls.builder()
                    .budget(b.getBudget())
                    .lgots(b.getLgots())
                    .university(university)
                    .build();
            ballsRepository.save(balls);
            return balls;
        } else {
            bal.setBudget(b.getBudget());
            bal.setLgots(b.getLgots());
            ballsRepository.save(bal);
            return bal;
        }
    }

    @Override
    public Balls getBalls(University university) {
        return ballsRepository.findByUniversity(university);
    }
}
