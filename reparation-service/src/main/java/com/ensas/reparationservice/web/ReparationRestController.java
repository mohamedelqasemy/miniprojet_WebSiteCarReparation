package com.ensas.reparationservice.web;

import com.ensas.reparationservice.dtos.CloudinaryResponse;
import com.ensas.reparationservice.dtos.ReparationDto;
import com.ensas.reparationservice.entities.Reparation;
import com.ensas.reparationservice.mappers.ReparationMapper;
import com.ensas.reparationservice.repositories.ReparationRepository;
import com.ensas.reparationservice.services.CloudinaryService;
import com.ensas.reparationservice.services.ReparationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/reparations")
@AllArgsConstructor
public class ReparationRestController {
    private final ReparationService reparationService;
    private final CloudinaryService cloudinaryService;
    private final ReparationRepository reparationRepository;

    //get all reparations
    @GetMapping
    public ResponseEntity<List<ReparationDto>> getAllReparations(){
        List<ReparationDto> reparationDtoList = reparationService.getAllReparations();
        return ResponseEntity.ok(reparationDtoList);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ReparationDto>> getPaginatedReparations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReparationDto> pageResult = reparationService.getPaginatedReparations(page, size);
        return ResponseEntity.ok(pageResult);
    }


    //get a specific reparation
    @GetMapping("/{id}")
    public ResponseEntity<ReparationDto> getReparationById(@PathVariable("id") Long id){
        ReparationDto reparationDto = reparationService.getReparationById(id);
        return ResponseEntity.ok(reparationDto);
    }

    //create a reparation
    @PostMapping
    public ResponseEntity<ReparationDto> createReparation(@RequestBody ReparationDto reparationDto){
        ReparationDto reparation = reparationService.createReparation(reparationDto);
        return ResponseEntity.ok(reparation);
    }

    @Transactional
    //update a reparation that exists.
    @PutMapping("/{id}")
    public ResponseEntity<ReparationDto> updateReparation(@PathVariable("id") Long id,@RequestBody ReparationDto reparationDto) {
        ReparationDto reparation = reparationService.updateReparation(id,reparationDto);
        return ResponseEntity.ok(reparation);
    }

    //delete a reparation that exists
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReparation (@PathVariable("id") Long id){
        reparationService.deleteReparation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cloudinary/upload")
    public CloudinaryResponse uploadToCloudinary(@RequestParam("image") MultipartFile image) {
        return cloudinaryService.uploadFile(image, image.getOriginalFilename());
    }

    @Transactional
    @PutMapping("/{id}/image")
    public ReparationDto updateReparationImage(@PathVariable("id") Long id, String imageUrl) {
        Reparation existing = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation non trouvée"));

        // Supprimer l’ancienne image si elle existe
        if (existing.getImage() != null) {
            cloudinaryService.deleteFileByUrl(existing.getImage());
        }

        // Mettre à jour avec la nouvelle image
        existing.setImage(imageUrl);
        reparationRepository.save(existing);

        return ReparationMapper.toReparationDto(existing);
    }


}