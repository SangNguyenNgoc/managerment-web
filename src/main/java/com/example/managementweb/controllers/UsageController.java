package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usage")
public class UsageController {
    private final IUsageInfoService usageInfoService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", usageInfoService.getAllBorrow());
        return "usage/index";
    }

    @GetMapping("return/{id}")
    public String detail(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes){
        if(usageInfoService.returnDevice(id) == null){
            redirectAttributes.addAttribute("error", "Lỗi không xác đinh");
        }
        return "redirect:/admin/usage";
    }

}
