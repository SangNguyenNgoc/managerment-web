package com.example.managementweb.models;

import com.example.managementweb.dtos.PersonDTO;
import com.example.managementweb.entities.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO toDTO(PersonEntity personEntity);

    PersonEntity toEntity(PersonDTO personDTO);
}
