package com.ensas.equipementservice.services;

import com.ensas.equipementservice.dtos.CloudinaryResponse;
import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.entities.ImageEquipment;
import com.ensas.equipementservice.feign.UserFeignClient;
import com.ensas.equipementservice.mappers.EquipmentMapper;
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

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserFeignClient userFeignClient;
    private final CloudinaryService cloudinaryService;

    public EquipmentDto createEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = EquipmentMapper.toEntity(equipmentDto);
        equipment = equipmentRepository.save(equipment);
        return EquipmentMapper.toDTO(equipment);
    }

    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return EquipmentMapper.toDTOList(equipments);
    }

    public EquipmentDto getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(EquipmentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));
    }
    @Transactional
    public EquipmentDto updateEquipment(Long id, EquipmentDto equipmentDto) {
        if(id == null || equipmentDto == null) {
            throw new IllegalArgumentException("Les données de l'équipement ne peuvent pas être nulles");
        }
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));

        if(equipmentDto.getDescription() != null) {
            existingEquipment.setDescription(equipmentDto.getDescription());
        }
        if(equipmentDto.getName() != null) {
            existingEquipment.setName(equipmentDto.getName());
        }
        if(equipmentDto.getPrice() != null) {
            existingEquipment.setPrice(equipmentDto.getPrice());
        }
        if(equipmentDto.getQuantity() != null) {
            existingEquipment.setQuantity(equipmentDto.getQuantity());
        }
//        if(equipmentDto.getImage() != null) {
//            existingEquipment.setImage(equipmentDto.getImage());
//        }
        return EquipmentMapper.toDTO(existingEquipment);
    }

    public void deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new RuntimeException("Equipement non trouvé");
        }
        equipmentRepository.deleteById(id);
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

    public Page<EquipmentDto> getAllEquipmentPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending()); // ou autre tri
        return equipmentRepository.findAll(pageable)
                .map(EquipmentMapper::toDTO);
    }

    public Page<EquipmentDto> getEquipmentPaginated(List<String> carList, List<String> typeList, String search, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Specification<Equipment> spec = Specification.where(null);

        spec = spec.and(EquipmentSpecification.hasCar(carList));
        spec = spec.and(EquipmentSpecification.hasType(typeList));
        spec = spec.and(EquipmentSpecification.hasName(search));
        spec = spec.and(EquipmentSpecification.priceBetween(minPrice, maxPrice));


        return equipmentRepository.findAll(spec, pageable)
                .map(EquipmentMapper::toDTO);
    }

    @Transactional
    public String uploadImage(final Long id, final MultipartFile file) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));

        // 1. Upload vers Cloudinary
        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, "equipement_" + id);

        // 2. Créer une nouvelle image et la lier à l’équipement
        ImageEquipment image = new ImageEquipment();
        image.setImageLink(cloudinaryResponse.getUrl());
        image.setEquipment(equipment); // Lien bidirectionnel

        // 3. Ajouter à la liste des images
        equipment.getImages().add(image);

        // 4. Sauvegarder l’équipement (cascade = ALL s'occupe du persist de l'image)
        equipmentRepository.save(equipment);

        return cloudinaryResponse.getUrl();
    }





}
