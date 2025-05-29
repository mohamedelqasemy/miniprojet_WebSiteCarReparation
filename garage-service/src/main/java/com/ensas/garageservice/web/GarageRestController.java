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
import org.springframework.security.access.prepost.PreAuthorize;
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
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<GarageDto> requests = garageService.getAllGaragePaginated(page, size);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/filtered/paginated")
    public ResponseEntity<Page<GarageDto>> getFilteredGarages(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(garageService.getFilteredGarages(keyword, page, size));
    }


    @GetMapping("/{garageId}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable Long garageId) {
        return garageService.getGarageById(garageId);
    }

    @PreAuthorize("hasAuthority('SUPER ADMIN')")
    @PostMapping
    public ResponseEntity<GarageDto> createGarage(@RequestBody GarageDto garageDto) {
        GarageDto savedGarage = garageService.createGarage(garageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGarage);
    }

    @PreAuthorize("hasAuthority('SUPER ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GarageDto> updateGarage(@PathVariable Long id, @RequestBody GarageDto garageDto) {
        GarageDto updatedGarage = garageService.updateGarage(id, garageDto);
        return ResponseEntity.ok(updatedGarage);
    }

    @PreAuthorize("hasAuthority('SUPER ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @PreAuthorize("hasAuthority('SUPER ADMIN')")
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = garageService.uploadImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }


}
