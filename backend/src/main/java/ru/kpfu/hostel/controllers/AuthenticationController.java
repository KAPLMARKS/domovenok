package ru.kpfu.hostel.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.hostel.security.auth.AuthenticationRequest;
import ru.kpfu.hostel.security.auth.AuthenticationResponse;
import ru.kpfu.hostel.security.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ){
//        return ResponseEntity.ok(authenticationService.register(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticated(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
