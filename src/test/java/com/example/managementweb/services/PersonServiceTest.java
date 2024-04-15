package com.example.managementweb.services;

import com.example.managementweb.dtos.PersonDTO;
import com.example.managementweb.services.interfaces.IPersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitWebConfig
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Test
    public void testFindById(){
        PersonDTO personDTO = personService.getById(3121410417L);
        Assertions.assertNotNull(personDTO);
        System.out.println("id: "+personDTO.getId());
        System.out.println("mail: "+personDTO.getEmail());
    }

    @Test
    public void testRegisterCorrectCase(){
        PersonDTO personDTO = new PersonDTO("3121410146", "dat@gmail.com", "12345", "Đạt", "CNTT", "KTPM", "0961198450");
        List<String> result = personService.register(personDTO);
        Assertions.assertEquals(result.get(0),"success");
    }

    @Test
    public void testRegisterFailId(){
        PersonDTO personDTO = new PersonDTO("312141016", "dat@gmail.com", "12345", "Đạt", "CNTT", "KTPM", "0961198450");
        List<String> result = personService.register(personDTO);
        System.out.println(result.get(0));
    }

}