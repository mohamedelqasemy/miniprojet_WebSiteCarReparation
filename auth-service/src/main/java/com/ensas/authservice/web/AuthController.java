package com.ensas.authservice.web;

import com.ensas.authservice.dto.JwtResponse;
import com.ensas.authservice.dto.LoginRequest;
import com.ensas.authservice.dto.ResponseMessage;
import com.ensas.authservice.dto.SignupRequest;
import com.ensas.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        try {
            authService.register(request);
            // Renvoie une réponse JSON avec un message
            return ResponseEntity.ok(new ResponseMessage("User registered successfully.",true));
        } catch (Exception e) {
            log.error("Error during registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred during registration.",false));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }

}
