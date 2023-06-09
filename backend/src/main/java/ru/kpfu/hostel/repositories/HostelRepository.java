package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.University;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> getAllByUniversity(University university);
}
