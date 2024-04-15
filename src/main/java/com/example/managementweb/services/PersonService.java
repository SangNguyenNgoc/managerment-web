package com.example.managementweb.services;

import com.example.managementweb.dtos.PersonDTO;
import com.example.managementweb.entities.PersonEntity;
import com.example.managementweb.entities.Role;
import com.example.managementweb.models.PersonMapper;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.ObjectsValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonService implements IPersonService {

    PersonRepository personRepository;

    PersonMapper personMapper;

    ObjectsValidator<PersonDTO> validator;

    @Override
    public List<PersonEntity> findAll() {
        return personRepository.findAll();
    }


    @Override
    public PersonDTO getById(Long id) {
        PersonEntity person = personRepository.findById(id).orElse(new PersonEntity());
        return personMapper.toDTO(person);
    }

    @Override
    public List<String> register(PersonDTO personDTO) {
        List<String> error = validator.validate(personDTO);
        if (error.isEmpty()){
            PersonEntity newPerson = personMapper.toEntity(personDTO);
            newPerson.setRole(Role.ROLE_ADMIN);
            newPerson.setPassword(new BCryptPasswordEncoder().encode(personDTO.getPassword()));
            newPerson.setStatus(true);
            personRepository.save(newPerson);
            return List.of("success");
        }
        return error;
    }


}
