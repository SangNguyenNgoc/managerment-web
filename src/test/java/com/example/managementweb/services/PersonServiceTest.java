package com.example.managementweb.services;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

@SpringBootTest
@SpringJUnitWebConfig
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private AppUtil appUtil;

    @Test
    void findAll() {
        List<PersonResponseDto> result = personService.findAll();
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
        PersonCreateDto personCreateDto = PersonCreateDto.builder()
                .id("16")
                .name("Nguyễn Ngọc Sang")
                .email("nngocsang39@gmail.com")
                .password("3121410417")
                .phoneNumber("0916921132")
                .department("CNTT")
                .profession("KTPM")
                .build();
        PersonResponseDto result = personService.create(personCreateDto);
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void update() {
    }
    @Test
    void delete(){
        PersonResponseDto result = personService.delete(16L);
        System.out.println(appUtil.toJson(result));
    }
    @Test
    void addPersonsFromExcel() {
        String filePath = "D:\\import_CSDL.xlsx";
        personService.addPersonsFromExcel(filePath);
    }
}