package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Receipt;
import ru.kpfu.hostel.models.student.Student;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt getReceiptByStudent(Student student);
}
