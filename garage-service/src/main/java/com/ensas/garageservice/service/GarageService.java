package com.ensas.garageservice.service;

import com.ensas.garageservice.dto.GarageDto;
import com.ensas.garageservice.entity.Garage;
import com.ensas.garageservice.mapper.GarageMapper;
import com.ensas.garageservice.repository.GarageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;


    public Page<GarageDto> getAllGaragePaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return garageRepository.findAll(pageable)
                .map(GarageMapper::toDto);
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

        existingGarage.setNom(garageDto.getNom());
        existingGarage.setAdresse(garageDto.getAdresse());
        existingGarage.setTelephone(garageDto.getTelephone());
        existingGarage.setNote(garageDto.getNote());
        existingGarage.setOuvertureDate(garageDto.getOuvertureDate());
        existingGarage.setFermetureDate(garageDto.getFermetureDate());
        existingGarage.setImage(garageDto.getImage());

        Garage saved = garageRepository.save(existingGarage);
        return GarageMapper.toDto(saved);
    }

    public void deleteGarage(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with ID: " + id));
        garageRepository.delete(garage);
    }
}
