package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.student.FormEducation;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.models.student.StudentCategory;

import java.io.InputStream;
import java.util.List;

public interface ExcelParsingStudentService {
    List<Student> parseStudent(InputStream inputStream, University university);

    List<Student> loadStudentBalls(InputStream inputStream, University university);

    int countRating(Student student, Balls balls);

}
