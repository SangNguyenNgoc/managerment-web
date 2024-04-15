package com.example.managementweb.services.interfaces;

import com.example.managementweb.dtos.PersonDTO;
import com.example.managementweb.entities.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPersonService {

    List<PersonEntity> findAll();
    PersonDTO getById(Long id);
    List<String>register(PersonDTO personDTO);
}
