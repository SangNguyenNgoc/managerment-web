package com.example.managementweb.controllers;

import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class HomeController {

    IPersonService personService;

    @GetMapping("/")
    public String home(HttpSession httpSession){
        log.info(SecurityUtil.getSessionUser());
        return "index";
    }

    @GetMapping("/home")
    public String admin(HttpSession httpSession){
        log.info(SecurityUtil.getSessionUser());
        return "admin";
    }


}
