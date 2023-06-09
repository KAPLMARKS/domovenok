package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.dto.StudentDto;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.repositories.StudentRepository;
import ru.kpfu.hostel.services.PlaceService;
import ru.kpfu.hostel.services.StudentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;
    private final PlaceService placeService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService, PlaceService placeService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.placeService = placeService;
        this.studentRepository = studentRepository;
    }

    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @PreAuthorize("hasAuthority('STUD_GORODOK') || hasAuthority('HOSTEL_MANAGER')")
    @GetMapping("/with-places")
    public ResponseEntity<List<Student>> getStudentsWithPlaces() {
        return ResponseEntity.ok(studentService.getStudentsWithPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getStudent(@PathVariable("id") int id) {
        return ResponseEntity.ok("student");
    }

    @PostMapping("/key")
    public ResponseEntity<Student> sendStudent(@RequestBody StudentDto.Request.StudentLoad student, @RequestHeader("universityHeader") String key) {
        return ResponseEntity.ok(studentService.loadStudentByKey(student, key));
    }

    @PostMapping("/many/key")
    public ResponseEntity<List<Student>> sendStudents(@RequestBody List<StudentDto.Request.StudentLoad> students, @RequestHeader("universityHeader") String key) {
        return ResponseEntity.ok(studentService.loadStudentsByKey(students, key));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Student>> importStudent(@RequestParam("file") MultipartFile multipartFile,
                                                       @AuthenticationPrincipal UserModel userModel) {
        try {
            return ResponseEntity.ok(studentService
                    .loadStudentByExcel(multipartFile.getInputStream(), userModel.getUniversity()));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Student> loadStudent(@RequestBody StudentDto.Request.StudentLoad student,
                                               @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(studentService.loadStudent(student, userModel.getUniversity()));
    }

    @PostMapping("/many/")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Student>> loadStudent(@RequestBody List<StudentDto.Request.StudentLoad> students,
                                                     @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(studentService.loadStudents(students, userModel.getUniversity()));
    }

    @PostMapping(value = "/import/balls", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Student>> importStudentBalls(@RequestParam("file") MultipartFile multipartFile,
                                                            @AuthenticationPrincipal UserModel userModel) {
        try {
            return ResponseEntity.ok(studentService
                    .loadStudentBalls(multipartFile.getInputStream(), userModel.getUniversity()));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/place")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Place> getStudentPlace(@AuthenticationPrincipal Student student) {
        return ResponseEntity.ok(placeService.getPlaceByStudent(student));
    }
    
    @PostMapping("/remove/{studentId}")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Student> removeStudent(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId).stream().findFirst().orElseThrow();
        return ResponseEntity.ok(studentService.removePlace(student));
    }
}
