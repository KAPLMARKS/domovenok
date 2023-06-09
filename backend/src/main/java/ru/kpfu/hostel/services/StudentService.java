package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.StudentDto;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.student.Student;

import java.io.InputStream;
import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    List<Student> getStudentsWithPlaces();

    Student loadStudentByKey(StudentDto.Request.StudentLoad student, String universityKey);

    List<Student> loadStudentsByKey(List<StudentDto.Request.StudentLoad> students, String universityKey);

    Student loadStudent(StudentDto.Request.StudentLoad student, University university);

    List<Student> loadStudents(List<StudentDto.Request.StudentLoad> students, University university);

    List<Student> loadStudentByExcel(InputStream inputStream, University university);

    List<Student> loadStudentBalls(InputStream inputStream, University university);

    void deleteStudent(Long studentId);

    Student addPlace(Student student, Place place);

    Student editPlace(Student student, Place place);

    Student removePlace(Student student);
}
