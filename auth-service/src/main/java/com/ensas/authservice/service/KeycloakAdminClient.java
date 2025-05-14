package com.ensas.authservice.service;
import com.ensas.authservice.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
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

    // Créer un utilisateur dans Keycloak et récupérer son ID
    public String createUserInKeycloak(SignupRequest request) {
        String accessToken = getAccessToken();

        String url = keycloakUrl + "/admin/realms/" + realm + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", request.getEmail());
        userPayload.put("email", request.getEmail());
        userPayload.put("firstName", request.getFirstname());
        userPayload.put("lastName", request.getLastname());
        userPayload.put("enabled", true);
        userPayload.put("credentials", List.of(Map.of("type", "password", "value", request.getPassword(), "temporary", false)));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userPayload, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

        return getUserIdByEmail(request.getEmail()); // Récupère l'ID après création
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

    // Assigner un rôle à l'utilisateur
    public void assignRoleToUser(String userId, String roleName) {
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String roleId = getRoleId(roleName);
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

        Map<String, Object> role = Map.of("id", roleId, "name", roleName);
        HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(List.of(role), headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }

    // Récupérer l'ID d'un rôle via son nom
    private String getRoleId(String roleName) {
        String accessToken = getAccessToken();
        String url = keycloakUrl + "/admin/realms/" + realm + "/roles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);

        for (Map<String, Object> role : response.getBody()) {
            if (roleName.equals(role.get("name"))) {
                return (String) role.get("id");
            }
        }
        throw new RuntimeException("Rôle non trouvé dans Keycloak.");
    }
}