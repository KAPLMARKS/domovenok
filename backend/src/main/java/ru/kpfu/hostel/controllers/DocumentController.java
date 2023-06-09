package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.services.ClaimService;
import ru.kpfu.hostel.services.DocumentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class DocumentController {

    private final DocumentService documentService;

    private final String UPLOAD_DIR = "C:/Users/Murot/Desktop/hostel/backend";

    @PostMapping(value = "/spravka", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> uploadSpravka(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Student student) {
        if (!file.isEmpty()) {
           String docUrl = createDocUrl(file);
            // Обработайте URL-адрес файла здесь, например, вызов сервисного метода.
            return ResponseEntity.ok(documentService.addSpravka(student, docUrl));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/privivki", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> uploadPrivivki(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Student student) {
        if (!file.isEmpty()) {
            String docUrl = createDocUrl(file);
            // Обработайте URL-адрес файла здесь, например, вызов сервисного метода.
            return ResponseEntity.ok(documentService.addPrivivki(student, docUrl));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/medPolis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> uploadMedPolis(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Student student) {
        if (!file.isEmpty()) {
            String docUrl = createDocUrl(file);
            // Обработайте URL-адрес файла здесь, например, вызов сервисного метода.
            return ResponseEntity.ok(documentService.addMedPolis(student, docUrl));
        }
        return ResponseEntity.badRequest().build();
    }

    public String createDocUrl(MultipartFile file) {
        // Определите путь к директории, где будет храниться файл.
        String directoryPath = UPLOAD_DIR;
        Path directory = Paths.get(directoryPath);

        // Сохраните файл на сервере.
        String fileName = file.getOriginalFilename();
        Path targetPath = directory.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Определите URL-адрес файла.
        return directoryPath + fileName;

    }
}
