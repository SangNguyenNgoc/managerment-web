package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeType;
import com.example.managementweb.models.dtos.person.PersonAddDto;
import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.AppUtil;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/person")
public class PersonController {

    private final IPersonService personService;

    private final IPenalizeService penalizeService;

    private final AppUtil appUtil;

    private static final List<PenalizeType> penalizeTypes = List.of(
            PenalizeType.builder().id(0).value("Khóa thẻ 1 tháng").build(),
            PenalizeType.builder().id(1).value("Khóa thẻ 2 tháng").build(),
            PenalizeType.builder().id(2).value("Khóa thẻ 1 tháng và bồi thường").build(),
            PenalizeType.builder().id(3).value("Bồi thường").build()
    );

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

    @GetMapping("/penalize/{personId}")
    public String createPenalizePage(
            @PathVariable("personId") Long userId,
            @ModelAttribute("penalize")PenalizeCreateDto penalize,
            Model m
    ) {
        PersonResponseDto person = personService.findByIdAndStatusTrue(userId);
        penalize.setPerson(person.getId());
        penalize.setPersonName(person.getName());
        m.addAttribute("penalizeList", penalizeTypes);
        return "person/create-penalty";
    }

    @PostMapping("/penalize")
    public String createPenalize(
            @Valid @ModelAttribute("penalize")PenalizeCreateDto penalize,
            RedirectAttributes redirectAttributes,
            Model m
    ) {
        if(Integer.parseInt(penalize.getType()) == 2 || Integer.parseInt(penalize.getType())==3) {
            if (penalize.getPayment() == null) {
                redirectAttributes.addAttribute("invalidPay","true");
                return "redirect:/admin/person/penalize/" + penalize.getPerson();
            }
        }
        penalize.setType(penalizeTypes.get(Integer.parseInt(penalize.getType())).getValue());
        PenalizeResponseDto result = penalizeService.create(penalize);
        redirectAttributes.addAttribute("successPenalize",result.getId());
        return "redirect:/admin/person/penalize/" + penalize.getPerson();
    }

    @PostMapping("/excel")
    @ResponseBody
    public ResponseEntity<String> importFile(@ModelAttribute("file") MultipartFile file) {
        Boolean result = personService.importFromExcel(file);
        if(result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failure");
        }
    }


}