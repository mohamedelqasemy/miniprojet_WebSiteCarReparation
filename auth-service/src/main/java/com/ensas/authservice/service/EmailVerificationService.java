package com.ensas.authservice.service;

import com.ensas.authservice.entities.VerificationToken;
import com.ensas.authservice.repositories.VerificationTokenRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @Value("${frontend.base-url}") // à mettre dans application.properties
    private String frontendBaseUrl;

    public EmailVerificationService(VerificationTokenRepository tokenRepository, JavaMailSender mailSender) {
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    public String generateAndSendToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new VerificationToken(token, email, LocalDateTime.now().plusHours(24)));
        sendEmail(email, token);
        return token;
    }

    private void sendEmail(String to, String token) {
        String subject = "Vérification de votre adresse email";
        String url = frontendBaseUrl + "/auth/verify-email?token=" + token;
        String body = "Bonjour,\n\nCliquez sur ce lien pour vérifier votre adresse email :\n" + url;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public boolean verifyToken(String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent() && optionalToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            //tokenRepository.deleteById(token); // Supprimer le token après vérification
            return true;
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        return tokenRepository.findByToken(token).map(VerificationToken::getEmail).orElse(null);
    }

}
