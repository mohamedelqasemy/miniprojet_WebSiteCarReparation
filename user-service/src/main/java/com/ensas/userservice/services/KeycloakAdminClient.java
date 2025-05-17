package com.ensas.userservice.services;
import com.ensas.userservice.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakAdminClient {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();


    public void updateUser(UserDto user) {
        String accessToken = getAccessToken();
        String userId = getUserIdByEmail(user.getEmail());
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, Object> updatePayload = new HashMap<>();
        updatePayload.put("username", user.getEmail());
        updatePayload.put("email", user.getEmail());
        updatePayload.put("firstName", user.getFirstname());
        updatePayload.put("lastName", user.getLastname());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(updatePayload, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    // Récupérer un token admin
    private String getAccessToken() {
        String url = keycloakUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=admin-cli&username=admin&password=admin&grant_type=password";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // Récupérer l'ID utilisateur à partir de son email
    private String getUserIdByEmail(String email) {
        String accessToken = getAccessToken();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);

        if (response.getBody() != null && response.getBody().length > 0) {
            return (String) response.getBody()[0].get("id");
        }
        throw new RuntimeException("Utilisateur non trouvé dans Keycloak.");
    }

}