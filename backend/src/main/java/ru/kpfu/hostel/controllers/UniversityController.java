package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.hostel.dto.BallsDto;
import ru.kpfu.hostel.dto.UniversityDto;
import ru.kpfu.hostel.models.Balls;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.services.UniversityService;

@RestController
@RequestMapping("/api/v1/university")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    @PostMapping("/refresh-secret-key")
    public ResponseEntity<UniversityDto.Response.SecretKey> refreshKey(@AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(universityService.refreshKey(userModel.getUniversity()));
    }

    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    @GetMapping("/secret-key")
    public ResponseEntity<UniversityDto.Request.SecretKey> getKey(@AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(universityService.getKey(userModel.getUniversity()));
    }

    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    @PostMapping("/balls")
    public ResponseEntity<Balls> editBalls(@RequestBody BallsDto.Request.BallsData balls, @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(universityService.editBalls(balls, userModel.getUniversity()));
    }

    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    @GetMapping("/show-balls")
    public ResponseEntity<Balls> getBalls(@AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(universityService.getBalls(userModel.getUniversity()));
    }
}
