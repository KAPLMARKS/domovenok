package ru.kpfu.hostel.services;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Employee;
import ru.kpfu.hostel.models.Roles;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.repositories.EmployeeRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final String defaultDolgnost = "Управляющий университетом";
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("forEmployee")
    private final ExcelParsingService<Employee> excelParsingService;
    private final UniversityService universityService;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
                               ExcelParsingService<Employee> excelParsingService, UniversityService universityService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.excelParsingService = excelParsingService;
        this.universityService = universityService;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee loadEmployee(EmployeeDto.Request.EmployeeLoad employee, String universityKey) {
        University university = universityService.findByKey(universityKey);
        return loadEmployee(employee, university);
    }

    @Validated
    @Override
    public Employee createAdmin(@Email String email, String password, University university) {
        Employee employee = Employee.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .university(university)
                .dolgnost(defaultDolgnost)
                .roles(Set.of(Roles.UNIVERSITY_ADMIN))
                .build();
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> loadEmployees(List<EmployeeDto.Request.EmployeeLoad> employees, String universityKey) {
        University university = universityService.findByKey(universityKey);
        return loadEmployees(employees, university);
    }

    @Override
    public Employee loadEmployee(EmployeeDto.Request.EmployeeLoad employee, University university) {
        return employeeRepository.save(Employee.builder()
                .dolgnost(employee.getDolgnost())
                .email(employee.getEmail())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .firstName(employee.getFirstName())
                .roles(Stream.of(employee.getRoles()).collect(Collectors.toSet()))
                .university(university)
                .password(passwordEncoder.encode(employee.getPassword()))
                .build()
        );
    }

    @Override
    public List<Employee> loadEmployees(List<EmployeeDto.Request.EmployeeLoad> employees, University university) {
        return employeeRepository.saveAll(
                employees.stream().map(e -> (Employee.builder()
                        .dolgnost(e.getDolgnost())
                        .email(e.getEmail())
                        .lastName(e.getLastName())
                        .middleName(e.getMiddleName())
                        .firstName(e.getFirstName())
                        .roles(Stream.of(e.getRoles()).collect(Collectors.toSet()))
                        .university(university)
                        .password(passwordEncoder.encode(e.getPassword()))
                        .build()
                )).collect(Collectors.toList())
        );
    }

    @Override
    public List<Employee> loadEmployees(InputStream inputStream, University university) {
        return employeeRepository.saveAll(excelParsingService.parseUsers(inputStream, university));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
