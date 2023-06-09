package ru.kpfu.hostel.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.exception.NotFoundElementException;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.repositories.UserModelRepository;
import ru.kpfu.hostel.security.auth.AuthenticationRequest;
import ru.kpfu.hostel.security.auth.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

//    public AuthenticationResponse register(RegisterRequest request) {
//
//        UserModel user = UserModel.builder()
//                .firstName(request.getFirstname())
//                .lastName(request.getLastname())
//                .email(request.getEmail())
//                .university()
//                .password(passwordEncoder.encode(request.getPassword()))
//                .roles(Stream.of(Roles.UNIVERSITY).collect(Collectors.toSet()))
//                .build();
//        userModelRepository.save(user);
//        var jwtToken=jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(passwordEncoder.encode(request.getPassword()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserModel user = userModelRepository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundElementException::new);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRoles().iterator().next())
                .build();
    }
}
