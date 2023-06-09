package ru.kpfu.hostel.services;

import jakarta.validation.constraints.Email;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Employee;
import ru.kpfu.hostel.models.University;

import java.io.InputStream;
import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee loadEmployee(EmployeeDto.Request.EmployeeLoad employee, String universityKey);

    Employee loadEmployee(EmployeeDto.Request.EmployeeLoad employee, University university);

    Employee createAdmin(@Email String email, String password, University university);

    List<Employee> loadEmployees(List<EmployeeDto.Request.EmployeeLoad> employees, String universityKey);

    List<Employee> loadEmployees(List<EmployeeDto.Request.EmployeeLoad> employees, University university);

    List<Employee> loadEmployees(InputStream inputStream, University university);

    void deleteEmployee(Long employeeId);
}
