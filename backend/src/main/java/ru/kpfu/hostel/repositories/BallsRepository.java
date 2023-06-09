package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.University;

@Repository
public interface BallsRepository  extends JpaRepository<Balls, Long> {

    Balls findByUniversity(University university);
}
