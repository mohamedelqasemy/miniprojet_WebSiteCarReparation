package com.ensas.iaservice.web;

import com.ensas.iaservice.services.IaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }


    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("image") MultipartFile image) {
        try {
            Map<String, Object> result = iaService.predictDamage(image);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur lors du traitement de l'image : " + e.getMessage()));
        }
    }
}
