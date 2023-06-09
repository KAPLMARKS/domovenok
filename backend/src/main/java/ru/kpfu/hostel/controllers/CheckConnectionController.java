package ru.kpfu.hostel.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckConnectionController {
    @GetMapping("/api/v1/check-connection")
    public ResponseEntity<String> getCheckConnection() {
        return ResponseEntity.ok("success");
    }
}
