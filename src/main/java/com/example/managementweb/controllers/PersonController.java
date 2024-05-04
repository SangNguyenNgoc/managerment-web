package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.person.PersonAddDto;
import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/person")
public class PersonController {

    private final IPersonService personService;
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
        model.addAttribute("list", personService.findByStatusTrue());
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonResponseDto personResponseDto = personService.findByIdAndStatusTrue(Long.valueOf(userId));
            model.addAttribute("user", personResponseDto.getName());
        }
        return "person/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        PersonResponseDto person = personService.findById(id);

        if (person != null) {
             model.addAttribute("person", person);
        } else {
        }
        return "person/detail";
    }
//    @GetMapping("/edit")
//    public String edit( @ModelAttribute("person") PersonUpdateDto editedPerson) {
//        return "person/detail";
//    }
    @PostMapping("/edit")
    public String edit( @Valid @ModelAttribute("person") PersonUpdateDto editedPerson,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "person/detail";
        }
        if (personService.update(editedPerson) == null) {
            redirectAttributes.addAttribute("existUser", "false");
            redirectAttributes.addFlashAttribute("person", editedPerson);
            return "redirect:/admin/person/detail/" + editedPerson.getId();
        }
        redirectAttributes.addAttribute("success", "Cập nhật thành công");
        return "redirect:/admin/person/detail/" + editedPerson.getId();
    }

    @GetMapping("/create")
    public String edit( @ModelAttribute("person") PersonResponseDto person) {
        return "person/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("person") PersonAddDto newPerson,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "person/create";
        }
        if (personService.existById(Long.valueOf(newPerson.getId()))) {
            redirectAttributes.addAttribute("existUser", "true");
            redirectAttributes.addFlashAttribute("person", newPerson);
            return "redirect:/admin/person/create";
        }
        personService.create(newPerson);
        redirectAttributes.addAttribute("success", "Thêm tài khoản thành công");
        return "person/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes
    ){
        if(personService.delete(id) == null){
            redirectAttributes.addAttribute("error", "not found");
            return "redirect:/admin/person/detail/" + id;
        }
        redirectAttributes.addAttribute("success", "Xoá thành công tài khoản " + id);
        return "redirect:/admin/person";
    }
}