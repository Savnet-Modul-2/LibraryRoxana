package com.example.library.mapper;

import com.example.library.dto.ExemplaryDto;
import com.example.library.entities.Exemplary;

import java.util.List;
import java.util.stream.Collectors;

public class ExemplaryMapper {
    public static Exemplary toEntity(ExemplaryDto exemplaryDto) {
        Exemplary exemplary = new Exemplary();
        exemplary.setId(exemplaryDto.getId());
        exemplary.setPublisher(exemplaryDto.getPublisher());
        exemplary.setMaxReservationDays(exemplaryDto.getMaxReservationDays());
        return exemplary;
    }

    public static ExemplaryDto toDto(Exemplary exemplary) {
        ExemplaryDto exemplaryDto = new ExemplaryDto();
        exemplaryDto.setId(exemplary.getId());
        exemplaryDto.setPublisher(exemplary.getPublisher());
        exemplaryDto.setMaxReservationDays(exemplary.getMaxReservationDays());
        return exemplaryDto;
    }

    public static List<ExemplaryDto> toDtoList(List<Exemplary> exemplaries) {
        return exemplaries.stream().map(ExemplaryMapper::toDto).collect(Collectors.toList());
    }
}
