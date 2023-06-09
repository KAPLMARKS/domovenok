package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.hostel.models.Statistics;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.services.StatisticsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN') or hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Statistics>> getStatistics(@AuthenticationPrincipal UserModel userModel) throws IOException, InterruptedException {
        return ResponseEntity.ok(statisticsService.getUniversityStatistics(userModel.getUniversity()));
    }
}
