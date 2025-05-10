package com.ensas.reparationservice.services;

import com.ensas.reparationservice.dtos.CloudinaryResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReparationService {
    
    private final ReparationRepository reparationRepository;
    private final CloudinaryService cloudinaryService;

    public ReparationService(ReparationRepository reparationRepository, CloudinaryService cloudinaryService) {
        this.reparationRepository = reparationRepository;
        this.cloudinaryService = cloudinaryService;
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
    public ReparationDto updateReparation(Long id, ReparationDto reparationDto) {
        if (reparationDto == null || id == null) throw new IllegalArgumentException("Reparation invalide");

        Reparation existing = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparation non trouvée"));

        // S’il y a une nouvelle image → supprimer l’ancienne et mettre à jour
        if (reparationDto.getImage() != null && !reparationDto.getImage().equals(existing.getImage())) {
            cloudinaryService.deleteFileByUrl(existing.getImage());
            existing.setImage(reparationDto.getImage());
        }

        // Mise à jour des autres champs
        if (reparationDto.getName() != null) existing.setName(reparationDto.getName());
        if (reparationDto.getType() != null) existing.setType(reparationDto.getType());
        if (reparationDto.getDescription() != null) existing.setDescription(reparationDto.getDescription());
        if (reparationDto.getDateOfCreation() != null) existing.setDateOfCreation(reparationDto.getDateOfCreation());
        if (reparationDto.getServicePrice() != null) existing.setServicePrice(reparationDto.getServicePrice());

        // Enregistrer les modifications dans la base
        reparationRepository.save(existing);

        return ReparationMapper.toReparationDto(existing);
    }



    @Transactional
    public void deleteReparation(Long id) {
        Reparation reparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation non trouvée"));

        if (reparation.getImage() != null && !reparation.getImage().isEmpty()) {
            cloudinaryService.deleteFileByUrl(reparation.getImage());
        }

        reparationRepository.delete(reparation);
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

    @Transactional
    public String uploadImage(final Long id, final MultipartFile file) {
        Reparation reparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparation non trouvée"));

        // Supprimer l'ancienne image si elle existe
        if (reparation.getImage() != null && !reparation.getImage().isEmpty()) {
            cloudinaryService.deleteFileByUrl(reparation.getImage());
        }

        // Upload de la nouvelle image
        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, "reparation_" + id);
        reparation.setImage(cloudinaryResponse.getUrl());

        reparationRepository.save(reparation);
        return cloudinaryResponse.getUrl();
    }

}
