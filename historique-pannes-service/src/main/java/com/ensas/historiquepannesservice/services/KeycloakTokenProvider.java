package com.ensas.historiquepannesservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class KeycloakTokenProvider {

    @Value("${keycloak.internal.token-uri}")
    private String tokenUri;

    @Value("${keycloak.internal.client-id}")
    private String clientId;

    @Value("${keycloak.internal.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", "internal-service-client");
        params.add("client_secret", "EDgVEOs4G9aIRMZ30ESI6CAVSVJww8Js");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
            System.out.println("Keycloak Token Response: " + response.getBody());
            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            System.err.println("Failed to get token from Keycloak: " + e.getMessage());
            throw e;
        }
    }
}
