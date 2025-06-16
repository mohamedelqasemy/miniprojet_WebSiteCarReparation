package com.ensas.equipementservice.services;

import com.ensas.equipementservice.dtos.CloudinaryResponse;
import com.ensas.equipementservice.dtos.CommentResponseDto;
import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.entities.Comment;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.entities.ImageEquipment;
import com.ensas.equipementservice.feign.UserFeignClient;
import com.ensas.equipementservice.mappers.EquipmentMapper;
import com.ensas.equipementservice.models.User;
import com.ensas.equipementservice.repositories.CommentRepository;
import com.ensas.equipementservice.repositories.EquipmentRepository;
import com.ensas.equipementservice.util.EquipmentSpecification;
import feign.FeignException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CommentRepository commentRepository;
    private final UserFeignClient userFeignClient;
    private final CloudinaryService cloudinaryService;

    public EquipmentDto createEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = EquipmentMapper.toEntity(equipmentDto);
        equipment = equipmentRepository.save(equipment);
        return EquipmentMapper.toDTO(equipment,userFeignClient);
    }

    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return EquipmentMapper.toDTOList(equipments,userFeignClient);
    }

    public Page<EquipmentDto> searchEquipmentsPaginated(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Equipment> equipmentPage = equipmentRepository.searchByKeyword(keyword, pageable);
        return equipmentPage.map(EquipmentMapper::toDtoSearch);
    }


    public EquipmentDto getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(equipment -> EquipmentMapper.toDTO(equipment, userFeignClient))
                .orElseThrow(() -> new NotFoundException("Equipement non trouvé"));
    }
    @Transactional
    public EquipmentDto updateEquipment(Long id, EquipmentDto equipmentDto) {
        Equipment equipement = equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Équipement non trouvé"));

        equipement.setName(equipmentDto.getName());
        equipement.setDescription(equipmentDto.getDescription());
        equipement.setPrice(equipmentDto.getPrice());
        equipement.setQuantity(equipmentDto.getQuantity());
        equipement.setCar(equipmentDto.getCar());
        equipement.setType(equipmentDto.getType());

        List<String> newImageLinks = equipmentDto.getImageLinks();

        if (newImageLinks != null && !newImageLinks.isEmpty()) {
            List<ImageEquipment> currentImages = equipement.getImages();
            List<ImageEquipment> updatedImages = new ArrayList<>();

            for (int i = 0; i < Math.min(3, newImageLinks.size()); i++) {
                String newLink = newImageLinks.get(i);
                if (newLink != null && !newLink.isBlank()) {
                    if (i < currentImages.size()) {
                        String oldLink = currentImages.get(i).getImageLink();
                        if (!oldLink.equals(newLink)) {
                            // Supprimer ancienne image de Cloudinary
                            cloudinaryService.deleteFileByUrl(oldLink);
                            // Mettre à jour l'image
                            currentImages.get(i).setImageLink(newLink);
                        }
                        updatedImages.add(currentImages.get(i));
                    } else {
                        // Nouvelle image
                        ImageEquipment img = new ImageEquipment();
                        img.setImageLink(newLink);
                        img.setEquipment(equipement);
                        updatedImages.add(img);
                    }
                }
            }

            equipement.getImages().clear();
            for (ImageEquipment img : updatedImages) {
                equipement.getImages().add(img);
            }
        }

        equipement = equipmentRepository.save(equipement);
        return EquipmentMapper.toDTO(equipement, userFeignClient);
    }


    @Transactional
    public void deleteEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));

        // Supprimer les images de Cloudinary
        if (equipment.getImages() != null) {
            equipment.getImages().forEach(img -> {
                cloudinaryService.deleteFileByUrl(img.getImageLink());
            });
        }

        // Supprimer l'équipement (et ses images avec cascade)
        equipmentRepository.delete(equipment);
    }


    // Vérifier si l'utilisateur existe en utilisant Feign
    public boolean isUserExists(Long userId) {
        try {
            boolean response = userFeignClient.checkUserExists(userId);
            return response ? true : false;
        } catch (FeignException e) {
            return false;
        }
    }

    public List<CommentResponseDto> getLast5Comments() {
        List<Comment> comments = commentRepository.findTop5ByOrderByDateDesc();

        return comments.stream().map(comment -> {
            User user = userFeignClient.getUserById(comment.getUserId()).getBody();
            return new CommentResponseDto(
                    comment.getText(),
                    user.getFirstname() + " " + user.getLastname(),
                    user.getImage(),
                    comment.getDate()
            );
        }).collect(Collectors.toList());
    }



    public Page<EquipmentDto> getAllEquipmentPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending()); // ou autre tri
        return equipmentRepository.findAll(pageable)
                .map(equipment -> EquipmentMapper.toDTO(equipment, userFeignClient));
    }

    public Page<EquipmentDto> getEquipmentPaginated(List<String> carList, List<String> typeList, String search, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Specification<Equipment> spec = Specification.where(null);

        spec = spec.and(EquipmentSpecification.hasCar(carList));
        spec = spec.and(EquipmentSpecification.hasType(typeList));
        spec = spec.and(EquipmentSpecification.hasName(search));
        spec = spec.and(EquipmentSpecification.priceBetween(minPrice, maxPrice));


        return equipmentRepository.findAll(spec, pageable)
                .map(equipment -> EquipmentMapper.toDTO(equipment, userFeignClient));
    }

    @Transactional
    public String uploadImage(final Long id, final MultipartFile file) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));

        // Upload vers Cloudinary
        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, "equipement_" + id);

        // Vérifier si la liste des images est initialisée
        if (equipment.getImages() == null) {
            equipment.setImages(new ArrayList<>());
        }

        // Créer l'image et l'associer à l'équipement
        ImageEquipment image = new ImageEquipment();
        image.setImageLink(cloudinaryResponse.getUrl());
        image.setEquipment(equipment); // relation bidirectionnelle

        // Ajouter à la liste
        equipment.getImages().add(image);

        // Sauvegarder (cascade = ALL gère aussi l’image)
        equipmentRepository.save(equipment);

        return cloudinaryResponse.getUrl();
    }






}
