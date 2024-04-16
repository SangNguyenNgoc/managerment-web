package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;

import java.util.List;

public interface IPersonService {

    List<PersonResponseDto> findAll();

    PersonResponseDto findById(Long id);

    PersonResponseDto create(PersonCreateDto personCreateDto);

    PersonResponseDto update(PersonUpdateDto personUpdateDto);
}
