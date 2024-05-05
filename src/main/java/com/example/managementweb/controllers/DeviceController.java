package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.util.AppUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/device")
public class DeviceController {

    private final IDeviceService iDeviceService;


    private final AppUtil appUtil;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", iDeviceService.findByStatusTrue());
        return "device/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Optional<DeviceResponseDto> personOptional = Optional.ofNullable(iDeviceService.findById(id));
        if (personOptional.isPresent()) {
            DeviceResponseDto device = personOptional.get();
             model.addAttribute("device", device);
        } else {
        }
        return "device/detail";
    }
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("device") DeviceUpdateDto edited,
                       BindingResult result,
                       RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "device/detail";
        }
        if (iDeviceService.update(edited) == null) {
            redirectAttributes.addAttribute("error", "false");
            redirectAttributes.addFlashAttribute("person", edited);
            return "redirect:/admin/device/detail/" + edited.getId();
        }

        redirectAttributes.addAttribute("success", "Cập nhật thành công");
        return "redirect:/admin/device/detail/" + edited.getId();
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("device", new DeviceUpdateDto());
        return "device/create";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("device") DeviceCreateDto newDevice,
                         BindingResult result,
                         RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "device/create";
        }
        if (iDeviceService.eisistById(appUtil.parseId(newDevice.getId()))) {
            redirectAttributes.addAttribute("error", "thiết bị đã tồn tại");
            redirectAttributes.addFlashAttribute("device", newDevice);
            return "redirect:/admin/device/create";
        }
        iDeviceService.create(newDevice);
        redirectAttributes.addAttribute("success", "Tạo thiết bị thành công");
        return "redirect:/admin/device/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes
    ){
        if(iDeviceService.delete(id) == null){
            redirectAttributes.addAttribute("error", "not found");
            return "redirect:/admin/device/detail/" + id;
        }
        redirectAttributes.addAttribute("success", "Xoá thành công thiết bị " + id);
        return "redirect:/admin/device";
    }
}