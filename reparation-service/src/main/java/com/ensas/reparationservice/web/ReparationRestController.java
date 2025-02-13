package com.ensas.reparationservice.web;

import com.ensas.reparationservice.dtos.ReparationDto;
import com.ensas.reparationservice.services.ReparationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparations")
@AllArgsConstructor
public class ReparationRestController {
    private final ReparationService reparationService;

    //get all reparations
    @GetMapping
    public ResponseEntity<List<ReparationDto>> getAllReparations(){
        List<ReparationDto> reparationDtoList = reparationService.getAllReparations();
        return ResponseEntity.ok(reparationDtoList);
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

    //update a reparation that exists .
    @PutMapping("/{id}")
    public ResponseEntity<ReparationDto> updateReparation(@PathVariable("id") Long id,@RequestBody ReparationDto reparationDto) {
        ReparationDto reparation = reparationService.updateReparation(id,reparationDto);
        return ResponseEntity.ok(reparation);
    }

    //delete a reparation that exists
    @DeleteMapping
    public ResponseEntity<?> deleteReparation (@PathVariable("id") Long id){
        reparationService.deleteReparation(id);
        return ResponseEntity.noContent().build();
    }
}
