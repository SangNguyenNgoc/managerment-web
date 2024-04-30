package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.interfaces.IDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/device")
public class DeviceController {

    @Autowired
    private final IDeviceService iDeviceService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", iDeviceService.findAll());
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
    public String edit(@ModelAttribute("device") DeviceUpdateDto edited) {
        DeviceResponseDto dto = iDeviceService.update(edited);

        return "redirect:/admin/device/detail/" + edited.getId();
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("device", new DeviceUpdateDto());
        return "device/create";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("device") DeviceCreateDto newDevice) {
        Random rd = new Random();
        newDevice.setId(String.valueOf(rd.nextLong(100000000)));
        iDeviceService.create(newDevice);
        return "redirect:/admin/device";
    }
}