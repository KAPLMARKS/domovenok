package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Employee;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.services.EmployeeService;
import ru.kpfu.hostel.services.ExcelParsingService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ExcelParsingService<Employee> excelParsingService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployee(@PathVariable("id") int id) {
        return ResponseEntity.ok("student");
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    public ResponseEntity<List<Employee>> importEmployee(@RequestParam("file") MultipartFile multipartFile,
                                                         @AuthenticationPrincipal UserModel userModel) {
        try {
            return ResponseEntity.ok(employeeService
                    .loadEmployees(multipartFile.getInputStream(), userModel.getUniversity()));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    public ResponseEntity<Employee> loadEmployee(@RequestBody EmployeeDto.Request.EmployeeLoad employee,
                                                 @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(employeeService.loadEmployee(employee, userModel.getUniversity()));
    }

    @PostMapping("/many/")
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    public ResponseEntity<List<Employee>> loadEmployees(@RequestBody List<EmployeeDto.Request.EmployeeLoad> employees,
                                                        @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(employeeService.loadEmployees(employees, userModel.getUniversity()));
    }

    @PostMapping("/key")
    public ResponseEntity<Employee> loadEmployeeByKey(@RequestBody EmployeeDto.Request.EmployeeLoad employee,
                                                      @RequestHeader("universityHeader") String key) {
        return ResponseEntity.ok(employeeService.loadEmployee(employee, key));
    }

    @PostMapping("/many/key")
    public ResponseEntity<List<Employee>> loadEmployeesByKey(@RequestBody List<EmployeeDto.Request.EmployeeLoad> employees,
                                                             @RequestHeader("universityHeader") String key) {
        return ResponseEntity.ok(employeeService.loadEmployees(employees, key));
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @DeleteMapping("/delete/{employeeId}")
    public void deleteUser(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}

