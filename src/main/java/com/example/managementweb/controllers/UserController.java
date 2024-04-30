package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
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
public class UserController {

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
            m.addAttribute("user", person.getName());
            if (filter == null) {
                List<DeviceResponseDto> devices = deviceService.findByStatusTrue();
                m.addAttribute("deviceList", devices);
            } else {
                List<DeviceResponseDto> devices = deviceService.getAllByName(filter);
                m.addAttribute("deviceList", devices);
            }

        }
        return "user/index";
    }


    @GetMapping("/home")
    public String admin(HttpSession httpSession){
        log.info(SecurityUtil.getSessionUser());
        return "admin/admin";
    }

    @GetMapping("/booking/{deviceId}")
    public String bookingPage(
            @PathVariable(value = "deviceId", required = false) Long deviceId,
            @ModelAttribute("booking") UsageInfoBookingRequestDto requestDto,
            Model m
    ) {
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonResponseDto person = personService.findById(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
            DeviceResponseDto deviceResponseDto = deviceService.findByIdAndStatusTrue(deviceId);
            requestDto.setDeviceName(deviceResponseDto.getName());
        }
        return "user/booking";
    }

    @GetMapping("/booking-success/{id}")
    public String bookingPage(
            @PathVariable("id") Long usageId,
            Model m
    ) {
        String userId = SecurityUtil.getSessionUser();
        if(userId != null) {
            PersonResponseDto person = personService.findById(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
        }
        UsageInfoBookingDto bookingResponse = usageInfoService.getById(usageId);
        m.addAttribute("usage", bookingResponse);
        return "user/booking-success";
    }

    @PostMapping("/booking")
    public String borrowDevice(
            @Valid @ModelAttribute("booking") UsageInfoBookingRequestDto request,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model m
    ) {
        String userId = SecurityUtil.getSessionUser();
        PersonResponseDto person = new PersonResponseDto();
        if(userId != null) {
            person = personService.findById(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
            request.setPersonId(person.getId());
        }
        if (result.hasErrors()) {
            return "user/booking";
        }
        if (penalizeService.isPenalize(person.getId())) {
            redirectAttributes.addAttribute("penalize", true);
            return "redirect:/booking/" + request.getDeviceId();
        }
        if (usageInfoService.existByBookingTime(request.getBookingTime(), request.getDeviceId()) ||
                usageInfoService.existByBorrowTime(request.getBookingTime(), request.getDeviceId())) {
            redirectAttributes.addAttribute("failure", true);
            return "redirect:/booking/" + request.getDeviceId();
        }
        UsageInfoBookingDto bookingResponse = usageInfoService.bookingDevice(request);
        return "redirect:/booking-success/" + bookingResponse.getId();
    }

}
