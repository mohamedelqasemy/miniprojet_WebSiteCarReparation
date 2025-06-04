package com.ensas.authservice.web;

import com.ensas.authservice.dto.JwtResponse;
import com.ensas.authservice.dto.LoginRequest;
import com.ensas.authservice.dto.ResponseMessage;
import com.ensas.authservice.dto.SignupRequest;
import com.ensas.authservice.service.AuthService;
import com.ensas.authservice.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

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

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        passwordResetService.requestPasswordReset(email);
        return ResponseEntity.ok(Map.of("message", "Email de réinitialisation envoyé"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour"));
    }

}
