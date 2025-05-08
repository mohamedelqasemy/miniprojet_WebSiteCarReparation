package com.ensas.garageservice.web;

import com.ensas.garageservice.dto.CloudinaryResponse;
import com.ensas.garageservice.dto.GarageDto;
import com.ensas.garageservice.entity.Garage;
import com.ensas.garageservice.service.CloudinaryService;
import com.ensas.garageservice.service.GarageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/garages")
@AllArgsConstructor()
public class GarageRestController {
    private final GarageService garageService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<Page<GarageDto>> getAllGaragesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Page<GarageDto> requests = garageService.getAllGaragePaginated(page, size);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{garageId}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable Long garageId) {
        return garageService.getGarageById(garageId);
    }

    @PostMapping
    public ResponseEntity<GarageDto> createGarage(@RequestBody GarageDto garageDto) {
        GarageDto savedGarage = garageService.createGarage(garageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGarage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageDto> updateGarage(@PathVariable Long id, @RequestBody GarageDto garageDto) {
        GarageDto updatedGarage = garageService.updateGarage(id, garageDto);
        return ResponseEntity.ok(updatedGarage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @PostMapping("/upload-image")
    public ResponseEntity<CloudinaryResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        CloudinaryResponse response = cloudinaryService.uploadFile(file, file.getOriginalFilename());
        return ResponseEntity.ok(response);
    }


}
