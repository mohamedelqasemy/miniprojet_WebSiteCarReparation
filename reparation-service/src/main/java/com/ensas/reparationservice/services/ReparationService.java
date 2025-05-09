package com.ensas.reparationservice.services;

import com.ensas.reparationservice.dtos.ReparationDto;
import com.ensas.reparationservice.entities.Reparation;
import com.ensas.reparationservice.mappers.ReparationMapper;
import com.ensas.reparationservice.repositories.ReparationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReparationService {
    
    private final ReparationRepository reparationRepository;

    public ReparationService(ReparationRepository reparationRepository) {
        this.reparationRepository = reparationRepository;
    }
    
    public List<ReparationDto> getAllReparations(){
        List<Reparation> reparations = reparationRepository.findAll();
        return ReparationMapper.toReparationDtoList(reparations);
    }
    
    public ReparationDto getReparationById(Long id){
        return reparationRepository.findById(id)
                .map(ReparationMapper::toReparationDto)
                .orElseThrow(() -> new RuntimeException("Reparation non trouvé"));
    }
    
    public ReparationDto createReparation(ReparationDto reparationDto){
        Reparation reparation = ReparationMapper.toReparation(reparationDto);
        reparationRepository.save(reparation);
        return ReparationMapper.toReparationDto(reparation);
    }

    @Transactional
    public ReparationDto updateReparation(Long id,ReparationDto reparationDto) {
        if (reparationDto == null || id == null) {
            throw new IllegalArgumentException("Les données de la Reparation ne peuvent pas être nulles");
        }

        Reparation existingReparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparation non trouvé"));

        if (reparationDto.getName() != null) {
            existingReparation.setName(reparationDto.getName());
        }
        if (reparationDto.getType() != null) {
            existingReparation.setType(reparationDto.getType());
        }
        if (reparationDto.getDateOfCreation() != null) {
            existingReparation.setDateOfCreation(reparationDto.getDateOfCreation());
        }
        if (reparationDto.getDescription() != null) {
            existingReparation.setDescription(reparationDto.getDescription());
        }
        if (reparationDto.getServicePrice() != null) {
            existingReparation.setServicePrice(reparationDto.getServicePrice());
        }
        if (reparationDto.getImage() != null) {
            existingReparation.setImage(reparationDto.getImage());
        }

        return ReparationMapper.toReparationDto(existingReparation);
    }
    
    public void deleteReparation(Long id){
        if(!reparationRepository.existsById(id))
            throw new RuntimeException("Reparation non trouvé");
        reparationRepository.deleteById(id);
    }
    
    public ReparationDto defaultReparationDto(){
        return ReparationDto.builder()
                .id(0L)
                .name("Not Available")
                .description("Not Available")
                .type("en atelier")
                .dateOfCreation(new Date())
                .servicePrice(0.0)
                .build();
    }

    public Page<ReparationDto> getPaginatedReparations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateOfCreation").descending());
        return reparationRepository.findAll(pageable)
                .map(reparation -> ReparationDto.builder()
                        .id(reparation.getId())
                        .name(reparation.getName())
                        .description(reparation.getDescription())
                        .type(reparation.getType())
                        .dateOfCreation(reparation.getDateOfCreation())
                        .servicePrice(reparation.getServicePrice())
                        .image(reparation.getImage())
                        .build());
    }
}
