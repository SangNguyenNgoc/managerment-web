package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.statistic.CountPerDate;
import com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime;
import com.example.managementweb.services.interfaces.IStatisticService;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UsageController {

    private final IUsageInfoService usageInfoService;

    private final IStatisticService statisticService;

    private final AppUtil appUtil;

    @GetMapping("/usage")
    public String index(Model model){
        model.addAttribute("list", usageInfoService.getAllBorrow());
        return "usage/index";
    }

    @GetMapping("/booking")
    public String bookingList(Model model){
        model.addAttribute("list", usageInfoService.getAllBooking());
        return "usage/booking";
    }

    @GetMapping("/usage/return/{id}")
    public String detail(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes){
        if(usageInfoService.returnDevice(id) == null){
            redirectAttributes.addAttribute("error", "Lỗi không xác đinh");
        }
        return "redirect:/admin/usage";
    }

    @GetMapping("/usage/get/{id}")
    public String getDevice(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes){
        if(!usageInfoService.checkBooking(id)) {
            redirectAttributes.addAttribute("error", "Chưa đến giờ lấy thiết bị.");
            return "redirect:/admin/booking";
        }
        usageInfoService.getDevice(id);
        return "redirect:/admin/booking";
    }

    @GetMapping("/statistic")
    public String statisticPage() {
        return "admin/statistic";
    }

    @GetMapping("/statistic/checkin")
    @ResponseBody
    public ResponseEntity<?> statisticCheckIn(
            @RequestParam("date") LocalDate localDate,
            @RequestParam("type") String type,
            @RequestParam(value = "search", required = false) String search
    ) {
        List<CountPerDate> result = null;
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),localDate.getDayOfMonth(), 0, 0);
        switch (type) {
            case "day": {
                if (search.isEmpty()) {
                    result = statisticService.statisticCheckinPerDay(dateTime.toLocalDate(), null);
                } else {
                    result = statisticService.statisticCheckinPerDay(localDate, search);
                }
                break;
            }
            case "month": {
                if (search.isEmpty()) {
                    result = statisticService.statisticCheckinPerMonth(dateTime.toLocalDate(), null);
                } else {
                    result = statisticService.statisticCheckinPerMonth(localDate, search);
                }
                break;
            }
            case "year": {
                if (search.isEmpty()) {
                    result = statisticService.statisticCheckinPerYear(dateTime.toLocalDate(), null);
                } else {
                    result = statisticService.statisticCheckinPerYear(localDate, search);
                }
                break;
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistic/penalize")
    @ResponseBody
    public ResponseEntity<?> statisticPenalize(
            @RequestParam("date") LocalDate localDate,
            @RequestParam("type") String type,
            @RequestParam(value = "search") String search
    ) {
        List<CountPerDate> result = null;
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),localDate.getDayOfMonth(), 0, 0);
        switch (type) {
            case "month": {
                if (search.isEmpty()) {
                    result = statisticService.statisticPenalizePerMonthNotPresent(dateTime.toLocalDate());
                } else {
                    result = statisticService.statisticPenalizePerMonthIsPresent(dateTime.toLocalDate());
                }
                break;
            }
            case "year": {
                if (search.isEmpty()) {
                    result = statisticService.statisticPenalizePerYearNotPresent(dateTime.getYear());
                } else {
                    result = statisticService.statisticPenalizePerYearIsPresent(dateTime.getYear());
                }
                break;
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistic/device")
    @ResponseBody
    public ResponseEntity<?> statisticDevice(
            @RequestParam("date") LocalDate localDate,
            @RequestParam("type") String type
    ) {
        List<DeviceBorrowingStatByTime> result = null;
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),localDate.getDayOfMonth(), 0, 0);
        System.out.println(dateTime);
        System.out.println(type);
        switch (type) {
            case "month": {
                result = statisticService.statisticDevicePerMonth(dateTime.getMonth().getValue(), localDate.getYear());
                break;
            }
            case "year": {
                result = statisticService.statisticDevicePerYear(localDate.getYear());
                break;
            }
        }
        return ResponseEntity.ok(result);
    }
}
