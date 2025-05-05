package com.ensas.iaservice.web;

import com.ensas.iaservice.services.IaService;
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
    public ResponseEntity<?> predict(@RequestParam("image") MultipartFile image) throws IOException {
        // Appeler le service IA pour traiter l'image et récupérer les résultats
        Map<String, Object> result = iaService.predictDamage(image);
        return ResponseEntity.ok(result); // Retourner la réponse au frontend
    }
}
