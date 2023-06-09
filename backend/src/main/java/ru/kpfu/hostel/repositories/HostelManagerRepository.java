package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.HostelManager;

@Repository
public interface HostelManagerRepository extends JpaRepository<HostelManager, Long> {
}
