package com.ensas.equipementservice.web;

import com.ensas.equipementservice.dtos.CommentRequestDto;
import com.ensas.equipementservice.dtos.RatingRequestDto;
import com.ensas.equipementservice.dtos.ResponseMessageDto;
import com.ensas.equipementservice.entities.Comment;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.entities.Rating;
import com.ensas.equipementservice.repositories.CommentRepository;
import com.ensas.equipementservice.repositories.EquipmentRepository;
import com.ensas.equipementservice.repositories.RatingRepository;
import com.ensas.equipementservice.services.EquipmentService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentInteractionController {

    private final EquipmentRepository equipmentRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final EquipmentService equipmentService;

    @PostMapping("/rate")
    public ResponseEntity<ResponseMessageDto> addRating(@RequestBody RatingRequestDto dto) {
        // Vérifier si l'utilisateur existe en appelant le Feign Client
        if (!equipmentService.isUserExists(dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessageDto("User not found", false));
        }

        // Valider que l'équipement existe
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found"));

        // Créer l'objet Rating
        Rating rating = Rating.builder()
                .stars(dto.getStars())
                .userId(dto.getUserId())
                .equipment(equipment)
                .build();

        // Sauvegarder l'évaluation
        ratingRepository.save(rating);

        // Retourner une réponse structurée
        return ResponseEntity.ok(new ResponseMessageDto("Rating added successfully", true));
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseMessageDto> addComment(@RequestBody CommentRequestDto dto) {
        // Vérifier si l'utilisateur existe en appelant le Feign Client
        System.out.println("print addcomment");
        if (!equipmentService.isUserExists(dto.getUserId())) {
            System.out.println("print addcomment user not found");
            return ResponseEntity.ok(new ResponseMessageDto("User Not Found", false));
        }

        // Valider que l'équipement existe
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found"));

        Comment comment = Comment.builder()
                .text(dto.getText())
                .userId(dto.getUserId())
                .date(new Date())
                .equipment(equipment)
                .build();

        commentRepository.save(comment);
        return ResponseEntity.ok(new ResponseMessageDto("Comment added successfully", true));
    }
}
