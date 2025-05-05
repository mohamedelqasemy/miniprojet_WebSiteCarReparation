package com.ensas.equipementservice.uploads;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class EquipementController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/equipments/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uniqueName = UUID.randomUUID() + "_" + originalFilename;
            Path imagePath = Paths.get(UPLOAD_DIR, uniqueName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, file.getBytes());
            return ResponseEntity.ok("/images/" + uniqueName); // Retourne le chemin accessible
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload");
        }
    }

    // Endpoint pour servir les images
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get("uploads/").resolve(filename);
        Resource resource = (Resource) new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // ajuster si nécessaire
                .body(resource);
    }
}