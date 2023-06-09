package ru.kpfu.hostel.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.hostel.models.UserModel;

@RestController
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
@RequestMapping("/api/v1/prediction")
public class PredictionController {

    @GetMapping
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN') or hasAuthority('STUD_GORODOK')")
    public ResponseEntity<String> getPrediction(@AuthenticationPrincipal UserModel userModel) {
        RestTemplate restTemplate = new RestTemplate();
        String pythonEndpoint = "http://127.0.0.1:5000/prediction";
        String response = restTemplate.getForObject(pythonEndpoint, String.class);
        return ResponseEntity.ok(response);
    }
}
