package com.example.managementweb.services;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.mappers.PersonMapper;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.services.interfaces.IPersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonService implements IPersonService {

    PersonRepository personRepository;

    PersonMapper personMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public List<PersonResponseDto> findAll() {
        List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonResponseDto findById(Long id) {
        PersonEntity person = personRepository.findById(id).orElse(null);
        return personMapper.toResponseDto(person);
    }

    @Override
    public PersonResponseDto create(PersonCreateDto personCreateDto) {
        PersonEntity personEntity = personMapper.toEntity(personCreateDto);
        personEntity.setPassword(passwordEncoder.encode(personEntity.getPassword()));
        personEntity.setStatus(true);
        personEntity.setRole(Role.ROLE_USER);
        PersonEntity result = personRepository.save(personEntity);
        return personMapper.toResponseDto(result);
    }

    @Override
    public PersonResponseDto update(PersonUpdateDto personUpdateDto) {
        PersonEntity person = personRepository.findById(Long.valueOf(personUpdateDto.getId())).orElse(null);
        if(person != null) {
            PersonEntity personAfterUpdate = personMapper.partialUpdate(personUpdateDto, person);
            PersonEntity result = personRepository.save(personAfterUpdate);
            return personMapper.toResponseDto(result);
        }
        return null;
    }
}
