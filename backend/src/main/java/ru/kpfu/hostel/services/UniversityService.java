package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.BallsDto;
import ru.kpfu.hostel.dto.UniversityDto;
import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.University;

public interface UniversityService {
    University findByKey(String universityKey);

    University createUniversity(String organizationName);

    UniversityDto.Response.SecretKey refreshKey(University university);

    UniversityDto.Request.SecretKey getKey(University university);

    Balls editBalls(BallsDto.Request.BallsData b, University university);

    Balls getBalls(University university);
}
