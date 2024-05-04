package com.example.managementweb.controllers.user;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class BookingController {

    IPersonService personService;

    IPenalizeService penalizeService;

    IDeviceService deviceService;

    IUsageInfoService usageInfoService;

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
            @Valid @ModelAttribute("booking") UsageInfoBookingRequestDto bookingRequest,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model m
    ) {
        String userId = SecurityUtil.getSessionUser();
        PersonResponseDto person = new PersonResponseDto();
        if(userId != null) {
            person = personService.findById(Long.valueOf(userId));
            m.addAttribute("user", person.getName());
            bookingRequest.setPersonId(person.getId());
        }
        if (result.hasErrors()) {
            return "user/booking";
        }
        if (penalizeService.isPenalize(person.getId())) {
            redirectAttributes.addAttribute("penalize", true);
            return "redirect:/booking/" + bookingRequest.getDeviceId();
        }
        if (usageInfoService.existByBookingTime(bookingRequest.getBookingTime(), bookingRequest.getDeviceId()) ||
                usageInfoService.existByBorrowTime(bookingRequest.getBookingTime(), bookingRequest.getDeviceId())) {
            redirectAttributes.addAttribute("failure", true);
            return "redirect:/booking/" + bookingRequest.getDeviceId();
        }
        UsageInfoBookingDto bookingResponse = usageInfoService.bookingDevice(bookingRequest);
        return "redirect:/booking-success/" + bookingResponse.getId();
    }
}
