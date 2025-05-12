package com.ensas.historiquepannesservice.mappers;

import com.ensas.historiquepannesservice.dtos.BreakdownHistoryDto;
import com.ensas.historiquepannesservice.entities.BreakdownHistory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BreakdownHistoryMapper {

    public static BreakdownHistory toEntity(BreakdownHistoryDto breakdownHistoryDto) {
        BreakdownHistory breakdownHistory = new BreakdownHistory();
        BeanUtils.copyProperties(breakdownHistoryDto, breakdownHistory);
        return breakdownHistory;
    }

    public static BreakdownHistoryDto toDto(BreakdownHistory breakdownHistory) {
        BreakdownHistoryDto breakdownHistoryDto = new BreakdownHistoryDto();
        BeanUtils.copyProperties(breakdownHistory, breakdownHistoryDto);

        return breakdownHistoryDto;
    }

    public static List<BreakdownHistory> toEntity(List<BreakdownHistoryDto> breakdownHistoriesDtos) {
        return breakdownHistoriesDtos.stream()
                .map(BreakdownHistoryMapper::toEntity)
                .collect(Collectors.toList());

    }
    public static List<BreakdownHistoryDto> toDto(List<BreakdownHistory> breakdownHistories) {
        return breakdownHistories.stream()
                .map(BreakdownHistoryMapper::toDto)
                .collect(Collectors.toList());

    }
}
