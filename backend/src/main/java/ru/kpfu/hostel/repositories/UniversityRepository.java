package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.hostel.models.University;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByKey(String key);
}
