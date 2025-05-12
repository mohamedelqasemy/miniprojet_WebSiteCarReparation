package com.ensas.garageservice.service;

import com.ensas.garageservice.dto.CloudinaryResponse;
import com.ensas.garageservice.dto.GarageDto;
import com.ensas.garageservice.entity.Garage;
import com.ensas.garageservice.mapper.GarageMapper;
import com.ensas.garageservice.repository.GarageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;
    private final CloudinaryService cloudinaryService;

    public Page<GarageDto> getAllGaragePaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return garageRepository.findAll(pageable)
                .map(GarageMapper::toDto);
    }

    public Page<GarageDto> getFilteredGarages(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").descending());
        Page<Garage> garages = garageRepository.findByKeyword(keyword, pageable);
        return garages.map(GarageMapper::toDto);
    }

    public ResponseEntity<GarageDto> getGarageById(Long garageId) {
        return garageRepository.findById(garageId)
                .map(garage -> ResponseEntity.ok(GarageMapper.toDto(garage)))
                .orElse(ResponseEntity.notFound().build());
    }

    public GarageDto createGarage(GarageDto garageDto) {
        Garage garage = GarageMapper.toEntity(garageDto);
        garage.setDateCreation(new Date());
        Garage savedGarage = garageRepository.save(garage);
        return GarageMapper.toDto(savedGarage);
    }


    public GarageDto updateGarage(Long id, GarageDto garageDto) {
        Garage existingGarage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with ID: " + id));

        // Si une nouvelle image est fournie
        if (garageDto.getImage() != null && !garageDto.getImage().isBlank()
                && !garageDto.getImage().equals(existingGarage.getImage())) {

            // Supprimer l'ancienne image de Cloudinary
            cloudinaryService.deleteFileByUrl(existingGarage.getImage());
            existingGarage.setImage(garageDto.getImage());
        }


        existingGarage.setNom(garageDto.getNom());
        existingGarage.setAdresse(garageDto.getAdresse());
        existingGarage.setTelephone(garageDto.getTelephone());
        existingGarage.setNote(garageDto.getNote());
        existingGarage.setOuvertureDate(garageDto.getOuvertureDate());
        existingGarage.setFermetureDate(garageDto.getFermetureDate());

        Garage saved = garageRepository.save(existingGarage);
        return GarageMapper.toDto(saved);
    }

    public void deleteGarage(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with ID: " + id));

        if (garage.getImage() != null) {
            cloudinaryService.deleteFileByUrl(garage.getImage());
        }
        garageRepository.delete(garage);
    }

    @Transactional
    public String uploadImage(final Long id, final MultipartFile file) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage non trouvée"));

        // Supprimer l'ancienne image si elle existe
        if (garage.getImage() != null && !garage.getImage().isEmpty()) {
            cloudinaryService.deleteFileByUrl(garage.getImage());
        }

        // Upload de la nouvelle image
        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, "garage_" + id);
        garage.setImage(cloudinaryResponse.getUrl());

        garageRepository.save(garage);
        return cloudinaryResponse.getUrl();
    }

}
