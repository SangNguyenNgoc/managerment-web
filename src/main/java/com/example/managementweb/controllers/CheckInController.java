package com.example.managementweb.controllers;

import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/checkIn")
public class CheckInController {
    private final IUsageInfoService usageInfoService;

    private final IPersonService personService;

    private final AppUtil appUtil;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", usageInfoService.getAllCheckIn());
        return "checkIn/index";
    }

    @GetMapping("/create")
    public String create(){
//        model.addAttribute("person", new PersonResponseDto());
        return "checkIn/person-check";
    }

    @PostMapping("/create")
    public String create(@RequestParam String id, RedirectAttributes redirectAttributes){
        if(appUtil.parseId(id) == null){
            redirectAttributes.addAttribute("error", "Mã thành viên không đúng định dạng");
            return "redirect:/admin/checkIn/create";
        }
        if(!usageInfoService.checkIn(appUtil.parseId(id))){
            redirectAttributes.addAttribute("error", "Mã thành viên không tồn tại");
            return "redirect:/admin/checkIn/create";
        }
        redirectAttributes.addAttribute("success", "Check in thành công");
        redirectAttributes.addFlashAttribute("person", personService.findById(appUtil.parseId(id)));
        return "redirect:/admin/checkIn/create";
    }

}
