package com.ensas.authservice.web;

import com.ensas.authservice.dto.EmailDto;
import com.ensas.authservice.dto.ResponseMessage;
import com.ensas.authservice.feign.UserServiceClient;
import com.ensas.authservice.service.EmailVerificationService;
import com.ensas.authservice.service.KeycloakAdminClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final UserServiceClient userServiceClient;
    private final KeycloakAdminClient keycloakAdminClient;

    @GetMapping("/verify-email")
    public ResponseEntity<ResponseMessage> verifyEmail(@RequestParam String token) {
        boolean valid = emailVerificationService.verifyToken(token);
        if (!valid) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Lien invalide ou expiré", false));
        }

        // Récupère l’email associé au token
        String email = emailVerificationService.getEmailFromToken(token);

        // Construit notre DTO EmailDto
        EmailDto body = new EmailDto(email);

        // Appelle le user-service via Feign, en envoyant le JSON { "email": "..." }
        userServiceClient.markEmailVerified(body);

        // … si tu veux mettre à jour Keycloak aussi …
        keycloakAdminClient.markEmailVerified(email);

        return ResponseEntity.ok(new ResponseMessage("Email vérifié avec succès", true));
    }

}

