package ru.kpfu.hostel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.StudentDto;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Roles;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.student.FormEducation;
import ru.kpfu.hostel.models.student.QualificationType;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.models.student.StudentCategory;
import ru.kpfu.hostel.repositories.StudentRepository;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UniversityService universityService;
    private final PasswordEncoder passwordEncoder;
    private final ExcelParsingStudentService excelParsingStudentService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UniversityService universityService, PasswordEncoder passwordEncoder, ExcelParsingStudentService excelParsingStudentService) {
        this.studentRepository = studentRepository;
        this.universityService = universityService;
        this.passwordEncoder = passwordEncoder;
        this.excelParsingStudentService = excelParsingStudentService;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentsWithPlaces() {
        return studentRepository.findAllByPlaceIsNotNull();
    }

    @Override
    public Student loadStudentByKey(StudentDto.Request.StudentLoad student, String universityKey) {
        University university = universityService.findByKey(universityKey);
        return saveStudent(student, university);
    }

    @Override
    public List<Student> loadStudentsByKey(List<StudentDto.Request.StudentLoad> students, String universityKey) {
        University university = universityService.findByKey(universityKey);
        return saveStudents(students, university);
    }

    @Override
    public Student loadStudent(StudentDto.Request.StudentLoad student, University university) {
        return saveStudent(student, university);
    }

    Student saveStudent(StudentDto.Request.StudentLoad student, University university) {
        return studentRepository.save(Student.builder()
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .middleName(student.getMiddleName())
                .lastName(student.getLastName())
                .password(passwordEncoder.encode(student.getPassword()))
                .isMale(student.getIsMale())
                .isNowStatement(student.getIsNowStatement())
                .citizenship(student.getCitizenship())
                .studentCategory(StudentCategory.valueOf(student.getStudentCategory()))
                .formEducation(FormEducation.valueOf(student.getFormEducation()))
                .qualificationType(QualificationType.valueOf(student.getQualificationType()))
                .course(student.getCourse())
                .faculty(student.getFaculty())
                .hasPreferential(student.getHasPreferential())
                .age(student.getAge())
                .roles(Stream.of(Roles.STUDENT).collect(Collectors.toSet()))
                .university(university)
                .build());
    }

    @Override
    public List<Student> loadStudents(List<StudentDto.Request.StudentLoad> students, University university) {
        return saveStudents(students, university);
    }

    List<Student> saveStudents(List<StudentDto.Request.StudentLoad> students, University university) {
        return studentRepository.saveAll(students.stream().map(s -> (Student.builder()
                .email(s.getEmail())
                .firstName(s.getFirstName())
                .middleName(s.getMiddleName())
                .lastName(s.getLastName())
                .password(passwordEncoder.encode(s.getPassword()))
                .isMale(s.getIsMale())
                .isNowStatement(s.getIsNowStatement())
                .citizenship(s.getCitizenship())
                .studentCategory(StudentCategory.valueOf(s.getStudentCategory()))
                .formEducation(FormEducation.valueOf(s.getFormEducation()))
                .qualificationType(QualificationType.valueOf(s.getQualificationType()))
                .course(s.getCourse())
                .faculty(s.getFaculty())
                .hasPreferential(s.getHasPreferential())
                .age(s.getAge())
                .roles(Stream.of(Roles.STUDENT).collect(Collectors.toSet()))
                .university(university)
                .build())).collect(Collectors.toList()));
    }

    @Override
    public List<Student> loadStudentByExcel(InputStream inputStream, University university) {

        return studentRepository.saveAll(excelParsingStudentService.parseStudent(inputStream, university));
    }

    @Override
    public List<Student> loadStudentBalls(InputStream inputStream, University university) {

        return studentRepository.saveAll(excelParsingStudentService.loadStudentBalls(inputStream, university));
    }

    @Override
    public void deleteStudent(Long studentId) {
    }

    @Override
    public Student addPlace(Student student, Place place) {
        student.setPlace(place);
        return studentRepository.save(student);
    }

    @Override
    public Student editPlace(Student student, Place place) {
        student.setPlace(place);
        return studentRepository.save(student);
    }

    @Override
    public Student removePlace(Student student) {
        student.setPlace(null);
        student.setNowStatement(false);
        return studentRepository.save(student);
    }
}
