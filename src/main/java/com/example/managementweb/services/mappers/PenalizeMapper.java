package com.example.managementweb.services.mappers;


import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PenalizeEntity;
import com.example.managementweb.models.entities.PersonEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PenalizeMapper {

    @Mappings({
            @Mapping(target = "date", source = "date", dateFormat = "dd-MM-yyyy")
    })
    PenalizeResponseDto toDto(PenalizeEntity penalizeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PenalizeEntity partialUpdate(
            PenalizeUpdateDto penalizeUpdateDto,
            @MappingTarget PenalizeEntity penalizeEntity
    );

}
