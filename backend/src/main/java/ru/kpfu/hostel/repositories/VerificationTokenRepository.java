package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.hostel.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
