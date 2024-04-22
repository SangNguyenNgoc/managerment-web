package com.example.managementweb.services.mappers;


import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;

import com.example.managementweb.models.entities.PenalizeEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PenalizeMapper {
    PenalizeEntity toEntity(PenalizeCreateDto penalizeCreateDto);
    PenalizeResponseDto toResponseDto(PenalizeEntity penalizeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PenalizeEntity partialUpdate(PenalizeUpdateDto PenalizeUpdateDto, @MappingTarget PenalizeEntity penalizeEntity);
}
