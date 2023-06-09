package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.hostel.models.Requisition;

import java.util.Optional;

public interface RequisitionRepository extends JpaRepository<Requisition, Long> {
    Optional<Requisition> findById(Long aLong);
}
