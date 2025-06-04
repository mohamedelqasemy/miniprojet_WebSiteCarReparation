package com.ensas.authservice.service;

import com.ensas.authservice.dto.UserDto;
import com.ensas.authservice.entities.PasswordResetToken;
import com.ensas.authservice.feign.UserServiceClient;
import com.ensas.authservice.repositories.PasswordResetTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepo;
    private final JavaMailSender mailSender;
    private final KeycloakAdminClient kcAdmin;
    private final UserServiceClient userServiceClient;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    public PasswordResetService(PasswordResetTokenRepository tokenRepo, JavaMailSender mailSender, KeycloakAdminClient kcAdmin, UserServiceClient userServiceClient) {
        this.tokenRepo = tokenRepo;
        this.mailSender = mailSender;
        this.kcAdmin = kcAdmin;
        this.userServiceClient = userServiceClient;
    }


    public void requestPasswordReset(String email) {
        // 1) Vérifier que l'utilisateur existe
        UserDto user = userServiceClient.getUserByEmail(email);

        // 2) Générer token aléatoire
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(24);
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setEmail(email);
        prt.setExpiryDate(expiry);
        tokenRepo.save(prt);

        // 3) Construire URL de réinitialisation
        String resetUrl = frontendBaseUrl + "/auth/reset-password?token=" + token;
        String body = "Bonjour " + user.getFirstname() + ",\n\n"
                + "Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe :\n"
                + resetUrl + "\n\n"
                + "Ce lien expire dans 24 heures.\n\n"
                + "Si vous n'avez pas demandé ce changement, ignorez ce message.";

        // 4) Envoyer email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Réinitialisation de votre mot de passe");
        mail.setText(body);
        mailSender.send(mail);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        // 5) Valider le token
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        if (prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepo.deleteByToken(token);
            throw new RuntimeException("Token expiré");
        }

        // 6) Récupérer l'email et supprimer le token
        String email = prt.getEmail();
        tokenRepo.deleteByToken(token);

        String kcUserId = kcAdmin.getUserIdByEmail(email);

        kcAdmin.resetPassword(kcUserId, newPassword, false);
    }
}
