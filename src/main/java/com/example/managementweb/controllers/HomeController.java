package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class HomeController {

    IPersonService personService;

    @GetMapping("/")
    public String home(Model m){
        String userId = SecurityUtil.getSessionUser();
        log.info(userId);
        if(userId != null) {
            PersonResponseDto person = personService.findById(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
        }
        return "user/index";
    }

    @GetMapping("/home")
    public String admin(HttpSession httpSession){
        log.info(SecurityUtil.getSessionUser());
        return "admin/admin";
    }


}
