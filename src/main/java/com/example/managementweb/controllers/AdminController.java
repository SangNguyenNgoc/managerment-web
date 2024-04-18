package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.models.entities.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/person")
public class AdminController {
    static List<PersonResponseDto> peopleList = generateRandomPersons(20);
    public static List<PersonResponseDto> generateRandomPersons(int count) {
        List<PersonResponseDto> personList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            PersonResponseDto person = PersonResponseDto.builder()
                    .id((long) (i + 1))
                    .name("Person " + (i + 1))
                    .department("Department " + random.nextInt(5))
                    .email("person" + (i + 1) + "@example.com")
                    .profession("Profession " + random.nextInt(5))
                    .phoneNumber("123456789")
                    .role(Role.ROLE_ADMIN)
                    .build();
            personList.add(person);
        }
        return personList;
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", peopleList);
        return "person/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Optional<PersonResponseDto> personOptional = peopleList.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();

        if (personOptional.isPresent()) {
            PersonResponseDto person = personOptional.get();
             model.addAttribute("person", person);
        } else {
        }

        return "person/detail";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("person") PersonResponseDto editedPerson) {
        System.out.println(editedPerson);
        for (int i = 0; i < peopleList.size(); i++) {
            PersonResponseDto person = peopleList.get(i);
            if (person.getId().equals(editedPerson.getId())) {
                peopleList.set(i, editedPerson);
                break;
            }
        }
        return "redirect:/admin/person/detail/" + editedPerson.getId();
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("person", new PersonResponseDto());
        return "person/create";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("person") PersonResponseDto newPerson) {
        newPerson.setId(peopleList.get(peopleList.size() - 1).getId() + 1);
        peopleList.add(newPerson);
        return "redirect:/admin/person";
    }

}