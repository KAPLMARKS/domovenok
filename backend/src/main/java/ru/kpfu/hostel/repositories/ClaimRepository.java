package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.claim.AnswerType;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.claim.Type;
import ru.kpfu.hostel.models.student.Student;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findAllByDeclarerUniversity(University university);

    List<Claim> findAllByDeclarer(UserModel userModel);

    List<Claim> findAllByAnswerTypeAndDeclarerUniversity(AnswerType answerType, University university);

    List<Claim> findAllByPlace_Room_Hostel(Hostel hostel);

    List<Claim> findAllByPlace_Room_HostelAndTypeAndStatusAndAnswerType(Hostel hostel, Type type, int status, AnswerType answerType);

    List<Claim> findAllByAnswerTypeAndDeclarerUniversityAndStatus(AnswerType answerType, University university, int status);
}
