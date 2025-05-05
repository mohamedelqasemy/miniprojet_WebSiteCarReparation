package com.ensas.iaservice.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class IaService {

    public Map<String, Object> predictDamage(MultipartFile file) throws IOException {
        // URL de FastAPI
        String url = "http://localhost:8000/predict"; // Adresse de FastAPI (Docker)

        // Création de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Créer un fichier FormData pour envoyer l'image
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename(); // Nom de l'image
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // Faire la requête POST vers FastAPI et récupérer la réponse
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody(); // Résultats de la prédiction
    }
}
