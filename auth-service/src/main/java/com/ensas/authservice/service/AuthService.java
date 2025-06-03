package com.ensas.authservice.service;

import com.ensas.authservice.dto.JwtResponse;
import com.ensas.authservice.dto.LoginRequest;
import com.ensas.authservice.dto.SignupRequest;
import com.ensas.authservice.dto.UserDto;
import com.ensas.authservice.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.Map;


@Service

public class AuthService {
    private final UserServiceClient userServiceClient;
    private final KeycloakAdminClient keycloakAdminClient;
    private final EmailVerificationService emailVerificationService;

    public AuthService(UserServiceClient userServiceClient, KeycloakAdminClient keycloakAdminClient, EmailVerificationService emailVerificationService) {
        this.userServiceClient = userServiceClient;
        this.keycloakAdminClient = keycloakAdminClient;
        this.emailVerificationService = emailVerificationService;
    }

    public void register(SignupRequest request) {
        // 1. Créer l'utilisateur dans Keycloak et récupérer son ID
        String userId = keycloakAdminClient.createUserInKeycloak(request);

        // 2. Assigner le rôle "USER" au nouvel utilisateur
        keycloakAdminClient.assignRoleToUser(userId, "USER");

        // 3. Enregistrer l'utilisateur dans user-service
        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .password(null)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .num(request.getNum())
                .address(request.getAddress())
                .enabled(true)
                .role("USER")
                .created(new Date())
                .build();

        userServiceClient.createUser(userDto);
        emailVerificationService.generateAndSendToken(request.getEmail());
    }

    public JwtResponse login(LoginRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/realms/atlas-car-repair/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Utilisation de MultiValueMap pour encoder correctement les paramètres
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", "frontend-client");
        formData.add("username", request.getEmail());
        formData.add("password", request.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    response.getBody().containsKey("access_token")) {

                //return new JwtResponse((String) response.getBody().get("access_token"));
                String accessToken = (String) response.getBody().get("access_token");

                UserDto userDto = userServiceClient.getUserByEmail(request.getEmail());

                return JwtResponse.builder()
                        .token(accessToken)
                        .type("Bearer")
                        .id(userDto.getId())
                        .email(userDto.getEmail())
                        .firstname(userDto.getFirstname())
                        .lastname(userDto.getLastname())
                        .address(userDto.getAddress())
                        .image(userDto.getImage())
                        .role(userDto.getRole())
                        .publicId(userDto.getPublicId())
                        .build();
            }

            throw new RuntimeException("Erreur d'authentification, réponse Keycloak : " + response.getBody());

        } catch (HttpClientErrorException e) {
            System.out.println("Status: " + e.getStatusCode());
            System.out.println("Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Erreur Keycloak : " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Échec de la connexion à Keycloak : " + e.getMessage());
        }
    }


}