package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PersonEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonEntity toEntity(PersonCreateDto personCreateDTO);

    PersonResponseDto toResponseDto(PersonEntity personEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonEntity partialUpdate(PersonUpdateDto personUpdateDto, @MappingTarget PersonEntity personEntity);
}
