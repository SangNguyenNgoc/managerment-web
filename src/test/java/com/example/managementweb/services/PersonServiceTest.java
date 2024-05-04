package com.example.managementweb.services;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

@SpringBootTest
@SpringJUnitWebConfig
class PersonServiceTest {

    @Autowired
    private IPersonService personService;

    @Autowired
    private AppUtil appUtil;

    @Test
    void findAll() {
        List<PersonResponseDto> result = personService.findAll();
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void findById() {
        PersonResponseDto result = personService.findById(1120480015L);
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void create() {
    }

    @Test
    void update() {
        PersonResponseDto result = personService.findByIdAndStatusTrue(3121420417L);
        System.out.println(appUtil.toJson(result));

        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id("3121420417")
                .name("Hulk Smash Green")
                .phoneNumber("0916921132")
                .department("CNTT")
                .profession("KTPM")
                .build();
        PersonResponseDto result1 = personService.update(personUpdateDto);
        System.out.println(appUtil.toJson(result1));
    }

    @Test
    void delete() {
        PersonResponseDto result = personService.delete(3122567832L);
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void addPersonsFromExcel() {

    }

    @Test
    void findByStatusTrue() {
        List<PersonResponseDto> result = personService.findByStatusTrue();
        System.out.println(appUtil.toJson(result));
        result.forEach(item -> Assertions.assertTrue(item.getStatus()));
    }

    @Test
    void findByIdAndStatusTrue() {
        PersonResponseDto result = personService.findByIdAndStatusTrue(1120480015L);
        System.out.println(appUtil.toJson(result));
        Assertions.assertTrue(result.getStatus());


        PersonResponseDto result1 = personService.findByIdAndStatusTrue(1120410311L);
        Assertions.assertNull(result1);
    }

    @Test
    void existById() {
        Assertions.assertTrue(personService.existById(1120480015L));
        Assertions.assertFalse(personService.existById(11L));
    }

    @Test
    void existByEmail() {
        Assertions.assertTrue(personService.existByEmail("nngocsang38@gmail.com"));
        Assertions.assertFalse(personService.existByEmail("11L"));
    }

    @Test
    void existByIdAndStatusTrue() {
        Assertions.assertTrue(personService.existByIdAndStatusTrue(1120480015L));
        Assertions.assertFalse(personService.existByIdAndStatusTrue(1120410311L));
    }

    @Test
    void importFromExcel() {
    }
}