package com.ensas.equipementservice.mappers;

import com.ensas.equipementservice.dtos.CommentResponseDto;
import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.entities.Comment;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.entities.ImageEquipment;

import com.ensas.equipementservice.entities.Rating;
import com.ensas.equipementservice.feign.UserFeignClient;
import com.ensas.equipementservice.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EquipmentMapper {

    public static EquipmentDto toDTO(Equipment equipment,UserFeignClient userFeignClient) {
        EquipmentDto dto = new EquipmentDto();
        BeanUtils.copyProperties(equipment, dto);

        // Images
        if (equipment.getImages() != null) {
            dto.setImageLinks(equipment.getImages().stream()
                    .map(ImageEquipment::getImageLink)
                    .collect(Collectors.toList()));
        }

        // Ratings
        if (equipment.getRatings() != null && !equipment.getRatings().isEmpty()) {
            double avg = equipment.getRatings().stream()
                    .mapToInt(Rating::getStars)
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(avg);
        }

        // Comments
        if (equipment.getComments() != null) {
            dto.setComments(equipment.getComments().stream()
                    .map(comment -> toCommentResponseDto(comment, userFeignClient))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    private static CommentResponseDto toCommentResponseDto(Comment comment, UserFeignClient userFeignClient) {
        User user = userFeignClient.getUserById(comment.getUserId()).getBody();
        return CommentResponseDto.builder()
                .text(comment.getText())
                .userName(user.getFirstname() + " " + user.getLastname())
                .userImage(user.getImage())
                .date(comment.getDate())
                .build();
    }

    public static Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        BeanUtils.copyProperties(equipmentDto, equipment);

        if (equipmentDto.getImageLinks() != null && !equipmentDto.getImageLinks().isEmpty()) {
            List<ImageEquipment> images = equipmentDto.getImageLinks().stream()
                    .map(link -> ImageEquipment.builder()
                            .imageLink(link)
                            .equipment(equipment) // établir la relation
                            .build())
                    .collect(Collectors.toList());

            equipment.setImages(images);
        }

        return equipment;
    }

    public static List<EquipmentDto> toDTOList(List<Equipment> equipments, UserFeignClient userFeignClient) {
        return equipments.stream()
                .map(equipment -> EquipmentMapper.toDTO(equipment,userFeignClient))
                .collect(Collectors.toList());
    }

    public static List<Equipment> toEntityList(List<EquipmentDto> equipmentDtos) {
        return equipmentDtos.stream()
                .map(EquipmentMapper::toEntity)
                .collect(Collectors.toList());
    }
}
