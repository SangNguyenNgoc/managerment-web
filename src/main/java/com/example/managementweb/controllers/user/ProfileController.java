package com.example.managementweb.controllers.user;

import com.example.managementweb.models.dtos.person.*;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.AppUtil;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ProfileController {

    IPersonService personService;

    IUsageInfoService usageInfoService;

    AppUtil appUtil;

    @GetMapping("/profile")
    public String profilePage(
            Model m,
            @ModelAttribute("profile") PersonUpdateDto profile,
            @ModelAttribute("changeEmail") ChangeEmailRequestDto changeEmail,
            @ModelAttribute("changePassword")ChangePasswordRequestDto changePassword
            ) {
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonResponseDto person = personService.findByIdAndStatusTrue(Long.valueOf(userId));
            PersonUpdateDto personUpdate = personService.findByIdAndStatusTrueToUpdate(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
            m.addAttribute("profile", personUpdate);
            m.addAttribute("email", person.getEmail());
        }
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("profile") PersonUpdateDto updateDto,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "user/profile";
        }
        personService.update(updateDto);
        redirectAttributes.addAttribute("success", true);
        return "redirect:/profile";
    }

    @PostMapping("/sendOtp")
    @ResponseBody
    public ResponseEntity<String> sendOtp(
            @RequestParam("email") String email
    ) {
        log.info(email);
        if (personService.existByEmail(email)) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng");
        }
        String userId = SecurityUtil.getSessionUser();
        if (userId != null) {
            personService.sendOtp(Long.valueOf(userId), email);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("Không tìm thấy người dùng");
    }

    @PostMapping("/changeEmail")
    public String changeEmail(
            @ModelAttribute("changeEmail") ChangeEmailRequestDto requestDto,
            RedirectAttributes redirectAttributes
    ) {
        String userId = SecurityUtil.getSessionUser();
        if (userId != null) {
            if (personService.checkOtp(Long.valueOf(userId), requestDto.getOtp()) && personService.checkPassword(Long.valueOf(userId), requestDto.getPassword())) {
                personService.changeEmail(Long.valueOf(userId));
                redirectAttributes.addAttribute("updateMailSuccess", true);
                return "redirect:/profile";
            } else {
                redirectAttributes.addAttribute("invalidOtpOrPass", true);
                return "redirect:/profile";
            }
        }
        redirectAttributes.addAttribute("failUpdateEmail", true);
        return "redirect:/profile";
    }

    @PostMapping("/password")
    public String changePassword(
            @ModelAttribute("changePassword") ChangePasswordRequestDto requestDto,
            RedirectAttributes redirectAttributes
    ) {
        String userId = SecurityUtil.getSessionUser();
        if (userId != null) {
            if (personService.checkPassword(Long.valueOf(userId), requestDto.getOldPassword())) {
                if(personService.checkConfirmPassword(requestDto.getNewPassword(),requestDto.getConfirmPassword())) {
                    personService.changePassword(Long.valueOf(userId), requestDto.getNewPassword());
                    redirectAttributes.addAttribute("updatePassSuccess", true);
                    return "redirect:/profile";
                }
                redirectAttributes.addAttribute("invalidConfirm", true);
                return "redirect:/profile";
            }
            redirectAttributes.addAttribute("invalidPass", true);
            return "redirect:/profile";
        }
        redirectAttributes.addAttribute("failUpdatePassword", true);
        return "redirect:/profile";
    }

    @GetMapping("/profile/penalize")
    public String penalizePage(
            Model m,
            @RequestParam(value = "date", required = false) LocalDate date
    ) {
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonAndPenaltiesDto person = personService.getPersonAndPenalizeById(Long.valueOf(userId), date);
            m.addAttribute("user", person.getName());
            m.addAttribute("penalties", person.getPenalties());
        }
        return "user/penalize";
    }

    @GetMapping("/profile/usage")
    public String usagePage(
            Model m,
            @RequestParam(value = "date", required = false) LocalDate date
    ) {
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonAndUsageDto person = personService.getPersonAndUsageById(Long.valueOf(userId), date);
            m.addAttribute("user", person.getName());
            m.addAttribute("usages", person.getUsageInfos().stream().filter(item -> item.getDevice() != null));
        }
        return "user/usage";
    }

    @GetMapping("/profile/usage/delete/{usageId}")
    public String usagePage(
            @PathVariable("usageId") Long usageId
    ) {
        usageInfoService.cancelBooking(usageId);
        return "redirect:/profile/usage";
    }
}
