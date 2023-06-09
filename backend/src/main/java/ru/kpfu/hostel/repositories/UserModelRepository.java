package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.hostel.models.UserModel;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}
