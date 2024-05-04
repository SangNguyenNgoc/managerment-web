package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.AppUtil;
import com.example.managementweb.util.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class HomeController {

    IPersonService personService;

    IDeviceService deviceService;

    IUsageInfoService usageInfoService;

    IPenalizeService penalizeService;

    AppUtil appUtil;

    @GetMapping("/")
    public String home(
            Model m,
            @RequestParam(value = "filter",required = false) String filter
    ){
        String userId = SecurityUtil.getSessionUser();
        log.info(userId);
        if(userId != null) {
            PersonResponseDto person = personService.findById(Long.valueOf(userId));
            if (person.getRole() == Role.ROLE_ADMIN) {
                return "redirect:/admin/person";
            }
            m.addAttribute("user", person.getName());
            List<DeviceResponseDto> devices;
            if (filter == null) {
                devices = deviceService.findByStatusTrue();
            } else {
                devices = deviceService.getAllByName(filter);
            }
            m.addAttribute("deviceList", devices);
        }
        return "user/index";
    }


    @GetMapping("/home")
    public String admin(HttpSession httpSession){
        log.info(SecurityUtil.getSessionUser());
        return "admin/admin";
    }





}
