package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.AppUtil;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AuthController {

    AppUtil appUtil;

    IPersonService personService;

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("register") PersonCreateDto register) {
        String user = SecurityUtil.getSessionUser();
        if(user != null) {
            return "user/index";
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("register") PersonCreateDto register,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        if (personService.existById(Long.valueOf(register.getId()))) {
            redirectAttributes.addAttribute("existUser", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register";
        }
        if (!register.getConfirmPassword().equals(register.getPassword())) {
            System.out.println(appUtil.toJson(register));
            redirectAttributes.addAttribute("invalidConfirm", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register";
        }
        personService.register(register);
        redirectAttributes.addAttribute("success", "true");
        redirectAttributes.addFlashAttribute("register", register);
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(){
        String user = SecurityUtil.getSessionUser();
        if(user != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam("forgot-id") Long id,
            RedirectAttributes redirectAttributes
    ) {
        if (!personService.existById(id)) {
            redirectAttributes.addAttribute("userNotFound", "true");
            return "redirect:/login";
        }
        personService.resetPassword(id);
        redirectAttributes.addAttribute("resetPass", "true");
        return "redirect:/login";
    }
}
