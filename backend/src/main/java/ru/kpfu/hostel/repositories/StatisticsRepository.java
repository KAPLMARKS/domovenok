package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Statistics;
import ru.kpfu.hostel.models.University;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    List<Statistics> findAllByHostel_University(University university);
    Statistics findAllByHostel(Hostel hostel);
}
