package com.ensas.historiquepannesservice.services;

import com.ensas.historiquepannesservice.dtos.BreakdownHistoryDto;
import com.ensas.historiquepannesservice.entities.BreakdownHistory;
import com.ensas.historiquepannesservice.mappers.BreakdownHistoryMapper;
import com.ensas.historiquepannesservice.repositories.BreakdownHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreakdownHistoryService {
    private final BreakdownHistoryRepository repository;

    public List<BreakdownHistoryDto> getAllBreakdownHistory() {
        List<BreakdownHistory> breakdownHistories = repository.findAll();
        return BreakdownHistoryMapper.toDto(breakdownHistories);
    }

    public BreakdownHistoryDto getBreakdownHistoryById(Long id) {
        return repository.findById(id)
                .map(BreakdownHistoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Historique non trouvé"));
    }

    public BreakdownHistoryDto createBreakdownHistory(BreakdownHistoryDto breakdownHistoryDto) {
        BreakdownHistory breakdownHistory = BreakdownHistoryMapper.toEntity(breakdownHistoryDto);
        breakdownHistory = repository.save(breakdownHistory);
        return BreakdownHistoryMapper.toDto(breakdownHistory);
    }

    public BreakdownHistoryDto updateBreakdownHistory(Long id, BreakdownHistoryDto breakdownHistoryDto) {
        if(id == null || breakdownHistoryDto == null) {
            throw new IllegalArgumentException("Les données de l'historique de panne ne peuvent pas être nulles");
        }
        BreakdownHistory existingHistory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histoire de panne non trouvé"));

        if(breakdownHistoryDto.getDescription() != null) {
            existingHistory.setDescription(breakdownHistoryDto.getDescription());
        }
        if(breakdownHistoryDto.getDatePanne() != null) {
            existingHistory.setDatePanne(breakdownHistoryDto.getDatePanne());
        }
        if(breakdownHistoryDto.getIsRepaired() != null) {
            existingHistory.setIsRepaired(breakdownHistoryDto.getIsRepaired());
        }
        if(breakdownHistoryDto.getCar() != null) {
            existingHistory.setCar(breakdownHistoryDto.getCar());
        }
        if(breakdownHistoryDto.getCarId() != null) {
            existingHistory.setCarId(breakdownHistoryDto.getCarId());
        }
        return BreakdownHistoryMapper.toDto(existingHistory);
    }

    public void deleteBreakdownHistory(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Historique de panne non trouvé");
        }
        repository.deleteById(id);
    }
}
