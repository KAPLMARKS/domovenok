package ru.kpfu.hostel.services;

import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.dto.StudentDto;
import ru.kpfu.hostel.exception.ParseExcelException;
import ru.kpfu.hostel.models.*;
import ru.kpfu.hostel.models.student.FormEducation;
import ru.kpfu.hostel.models.student.QualificationType;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.models.student.StudentCategory;
import ru.kpfu.hostel.repositories.StudentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Validated
@Service
@Qualifier("forEmployee")
public class ExcelParsingEmployeeService implements ExcelParsingService<Employee>, ExcelParsingHostelManagerService {
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;


    public ExcelParsingEmployeeService(PasswordEncoder passwordEncoder, StudentRepository studentRepository) {

        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Employee> parseUsers(InputStream inputStream, University university) {
        try {
            XSSFWorkbook xssfWorkbook = getWorkbook(inputStream);
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            List<Employee> employees = new ArrayList<>();
            for (Row row : sheet) {
                Cell emailCell = row.getCell(0);
                if (emailCell.getStringCellValue().isEmpty()) {
                    break;
                }
                Cell passwordCell = row.getCell(1);
                Cell firstNameCell = row.getCell(2);
                Cell lastNameCell = row.getCell(3);
                Cell middleNameCell = row.getCell(4);
                Cell dolgnostCell = row.getCell(5);
                Cell roleCell = row.getCell(6);
                EmployeeDto.Request.EmployeeLoad employeeLoad = EmployeeDto.Request.EmployeeLoad.builder()
                        .email(emailCell.getStringCellValue())
                        .password(passwordCell.getStringCellValue())
                        .lastName(lastNameCell.getStringCellValue())
                        .firstName(firstNameCell.getStringCellValue())
                        .middleName(middleNameCell.getStringCellValue())
                        .dolgnost(dolgnostCell.getStringCellValue())
                        .roles(Roles.valueOf(roleCell.getStringCellValue()))
                        .build();
                employees.add(getEmployee(employeeLoad, university));
            }
            return employees;
        } catch (IOException e) {
            throw new ParseExcelException();
        }
    }

    @Override
    public List<HostelManager> parseHostelManager(InputStream inputStream, University university, Hostel hostel) {
        try {
            XSSFWorkbook xssfWorkbook = getWorkbook(inputStream);
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            List<HostelManager> hostelManagers = new ArrayList<>();
            for (Row row : sheet) {
                Cell emailCell = row.getCell(0);
                if (emailCell.getStringCellValue().isEmpty()) {
                    break;
                }
                Cell passwordCell = row.getCell(1);
                Cell firstNameCell = row.getCell(2);
                Cell lastNameCell = row.getCell(3);
                Cell middleNameCell = row.getCell(4);
                Cell dolgnostCell = row.getCell(5);
                Cell roleCell = row.getCell(6);
                EmployeeDto.Request.EmployeeLoad employeeLoad = EmployeeDto.Request.EmployeeLoad.builder()
                        .email(emailCell.getStringCellValue())
                        .password(passwordCell.getStringCellValue())
                        .lastName(lastNameCell.getStringCellValue())
                        .firstName(firstNameCell.getStringCellValue())
                        .middleName(middleNameCell.getStringCellValue())
                        .dolgnost(dolgnostCell.getStringCellValue())
                        .roles(Roles.valueOf(roleCell.getStringCellValue()))
                        .build();
                hostelManagers.add(getHostelManager(employeeLoad, university, hostel));
            }
            return hostelManagers;
        } catch (IOException e) {
            throw new ParseExcelException();
        }
    }


    private XSSFWorkbook getWorkbook(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }

    private Employee getEmployee(@Valid EmployeeDto.Request.EmployeeLoad employee, University university) {
        return Employee.builder()
                .email(employee.getEmail())
                .password(passwordEncoder.encode(employee.getPassword()))
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .firstName(employee.getFirstName())
                .dolgnost(employee.getDolgnost())
                .roles(Stream.of(employee.getRoles()).collect(Collectors.toSet()))
                .university(university)
                .build();
    }

    private HostelManager getHostelManager(@Valid EmployeeDto.Request.EmployeeLoad hostelManager, University university, Hostel hostel) {
        return HostelManager.builder()
                .email(hostelManager.getEmail())
                .password(passwordEncoder.encode(hostelManager.getPassword()))
                .lastName(hostelManager.getLastName())
                .middleName(hostelManager.getMiddleName())
                .firstName(hostelManager.getFirstName())
                .dolgnost(hostelManager.getDolgnost())
                .roles(Stream.of(hostelManager.getRoles()).collect(Collectors.toSet()))
                .university(university)
                .hostel(hostel)
                .build();
    }
}
