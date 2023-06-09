package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.dto.ClaimDto;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.claim.AnswerType;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.claim.Type;
import ru.kpfu.hostel.services.ClaimService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/claims")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class ClaimController {
    private final ClaimService claimService;

    @GetMapping
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN') or hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Claim>> getClaims(@AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(claimService.getClaims(userModel));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody ClaimDto.Response.CreateClaim createClaim, @RequestParam Long placeId,
                                             @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(claimService.createClaim(createClaim, userModel, placeId));
    }

//    private final String UPLOAD_DIR = "C:/Users/Murot/Desktop/hostel/backend";
//
//    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Claim> uploadFile(@RequestParam("message") String message,
//                                            @RequestParam("type") Type type,
//                                            @RequestParam("file") MultipartFile file,
//                                            @AuthenticationPrincipal UserModel userModel) {
//        if (!file.isEmpty()) {
//            // Определите путь к директории, где будет храниться файл.
//            String directoryPath = UPLOAD_DIR;
//            Path directory = Paths.get(directoryPath);
//
//            // Сохраните файл на сервере.
//            String fileName = file.getOriginalFilename();
//            Path targetPath = directory.resolve(fileName);
//            try {
//                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Определите URL-адрес файла.
//            String docUrl = directoryPath + fileName;
//
//            // Обработайте URL-адрес файла здесь, например, вызов сервисного метода.
//            return ResponseEntity.ok(claimService.createClaimWithDoc(message, type, docUrl, userModel));
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Claim>> getClaimsByStudent(@AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(claimService.getClaimsByStudent(userModel));
    }

    @GetMapping("/answerType")
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN') or hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Claim>> getClaims(@RequestParam AnswerType answerType, @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(claimService.getClaimsByStudGorodok(answerType, userModel));
    }

    @PutMapping("/success")
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN') or hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Claim> successClaim(@RequestParam Long claimId) {
        return  ResponseEntity.ok(claimService.successClaim(claimId));
    }

    @PutMapping("/declined")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Claim> declinedClaim(@RequestParam Long claimId) {
        return  ResponseEntity.ok(claimService.declinedClaim(claimId));
    }

    @PutMapping("/editStatus")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Claim> editClaim(@RequestParam Long claimId) {
        return  ResponseEntity.ok(claimService.editClaim(claimId));
    }

}