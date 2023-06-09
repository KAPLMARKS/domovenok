package ru.kpfu.hostel.services;

import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.hostel.dto.StudentDto;
import ru.kpfu.hostel.exception.ParseExcelException;
import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.Roles;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.student.FormEducation;
import ru.kpfu.hostel.models.student.QualificationType;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.models.student.StudentCategory;
import ru.kpfu.hostel.repositories.BallsRepository;
import ru.kpfu.hostel.repositories.StudentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class ExcelParsingStudentServiceImpl implements ExcelParsingStudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final BallsRepository ballsRepository;

    public ExcelParsingStudentServiceImpl(PasswordEncoder passwordEncoder, StudentRepository studentRepository, BallsRepository ballsRepository) {

        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.ballsRepository = ballsRepository;
    }

    @Override
    public List<Student> parseStudent(InputStream inputStream, University university) {
        try {
            XSSFWorkbook xssfWorkbook = getWorkbook(inputStream);
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            List<Student> students = new ArrayList<>();
            for (Row row : sheet) {
                Cell emailCell = row.getCell(0);
                if (emailCell == null || emailCell.getStringCellValue().isEmpty()) {
                    break;
                }
                Cell passwordCell = row.getCell(1);
                Cell firstNameCell = row.getCell(2);
                Cell lastNameCell = row.getCell(3);
                Cell middleNameCell = row.getCell(4);
                Cell isMaleCell = row.getCell(5);
                Cell isNowStatement = row.getCell(6);
                Cell citizenshipCell = row.getCell(7);
                Cell studentCategoryCell = row.getCell(8);
                Cell formEducationCell = row.getCell(9);
                Cell qualificationTypeCell = row.getCell(10);
                Cell courseCell = row.getCell(11);
                Cell facultyCell = row.getCell(12);
                Cell hasPreferentialCell = row.getCell(13);
                Cell ageCell = row.getCell(14);
                StudentDto.Request.StudentLoad studentLoad = StudentDto.Request.StudentLoad.builder()
                        .email(emailCell.getStringCellValue())
                        .password(passwordCell.getStringCellValue())
                        .lastName(lastNameCell.getStringCellValue())
                        .firstName(firstNameCell.getStringCellValue())
                        .middleName(middleNameCell.getStringCellValue())
                        .isMale(isMaleCell.getBooleanCellValue())
                        .isNowStatement(isNowStatement.getBooleanCellValue())
                        .citizenship(citizenshipCell.getStringCellValue())
                        .studentCategory(studentCategoryCell.getStringCellValue())
                        .formEducation(formEducationCell.getStringCellValue())
                        .qualificationType(qualificationTypeCell.getStringCellValue())
                        .course((int) courseCell.getNumericCellValue())
                        .faculty(facultyCell.getStringCellValue())
                        .hasPreferential(hasPreferentialCell.getBooleanCellValue())
                        .age((int) ageCell.getNumericCellValue())
                        .build();
                students.add(getStudent(studentLoad, university));
            }
            return students;
        } catch (IOException e) {
            throw new ParseExcelException();
        }
    }

    @Override
    public List<Student> loadStudentBalls(InputStream inputStream, University university) {
        try {
            Balls balls = ballsRepository.findByUniversity(university);
            XSSFWorkbook xssfWorkbook = getWorkbook(inputStream);
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            List<Student> students = new ArrayList<>();
            for (Row row : sheet) {
                Cell emailCell = row.getCell(0);
                if (emailCell == null || emailCell.getStringCellValue().isEmpty()) {
                    break;
                }
                Cell activityBallsCell = row.getCell(15);
                Student student = studentRepository.findOneByEmail(emailCell.getStringCellValue());
                student.setActivityBalls((int)activityBallsCell.getNumericCellValue());
                student.setRating(countRating(student, balls));
                students.add(student);
            }
            return students;
        } catch (IOException e) {
            throw new ParseExcelException();
        }
    }


    private Student getStudent(@Valid StudentDto.Request.StudentLoad student, University university) {
        return Student.builder()
                .email(student.getEmail())
                .password(passwordEncoder.encode(student.getPassword()))
                .lastName(student.getLastName())
                .middleName(student.getMiddleName())
                .firstName(student.getFirstName())
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
                .build();
    }

    @Override
    public int countRating(Student student, Balls balls) {
        int countBalls = 0;
        if(student.getFormEducation().equals(FormEducation.FULL_TIME)) {
            if (student.getCourse() == 1) {
                if (student.getStudentCategory().equals(StudentCategory.BUDGET)) {
                    countBalls = countBalls + balls.getBudget();
                }
                if(student.isHasPreferential()) {
                    countBalls = countBalls + balls.getLgots();
                }
            }
        }
        return  countBalls + student.getActivityBalls();
    }

    private XSSFWorkbook getWorkbook(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }
}
