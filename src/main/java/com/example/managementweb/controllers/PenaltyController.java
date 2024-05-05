package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.util.AppUtil;
import com.example.managementweb.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/penalty")
public class PenaltyController {
    private final IPenalizeService penalizeService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", penalizeService.findAll());
        return "penalty/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Optional<PenalizeResponseDto> penaltyOptional = Optional.ofNullable(penalizeService.findById(id));
        if (penaltyOptional.isPresent()) {
            PenalizeResponseDto penalizeResponseDto = penaltyOptional.get();
            model.addAttribute("penalty", penalizeResponseDto);
        } else {
        }
        return "penalty/detail";
    }

    @PostMapping("/edit/{id}")
    public String edit( @PathVariable("id") Long id,
                        @Valid @ModelAttribute("penalty") PenalizeUpdateDto editedPenalty,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        System.out.println(new AppUtil().toJson(editedPenalty));
        editedPenalty.setId(id);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            PenalizeResponseDto penalty = Optional.ofNullable(penalizeService.findById(id)).get();
            editedPenalty.setPerson(penalty.getPerson());
            editedPenalty.setDate(penalty.getDate());
            return "penalty/detail";
        }
        if (penalizeService.update(editedPenalty) == null) {
            System.out.println("Lỗi");
            redirectAttributes.addAttribute("error", "Không thể cập nhật");
            return "redirect:/admin/penalty/detail/" + id;
        }
        redirectAttributes.addAttribute("success", "Cập nhật thành công");
        return "redirect:/admin/penalty/detail/" + id;
    }
}
