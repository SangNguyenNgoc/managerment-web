package com.example.managementweb.controllers;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.dtos.usageInfo.BorrowRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;
import com.example.managementweb.services.PenalizeService;
import com.example.managementweb.services.PersonService;
import com.example.managementweb.services.UsageInfoService;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.util.AppUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/device")
public class DeviceController {

    private final IDeviceService deviceService;

    private final UsageInfoService usageInfoService;

    private final PersonService personService;

    private final PenalizeService penalizeService;

    private final AppUtil appUtil;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list", deviceService.findByStatusTrue());
        return "device/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Optional<DeviceResponseDto> personOptional = Optional.ofNullable(deviceService.findById(id));
        if (personOptional.isPresent()) {
            DeviceResponseDto device = personOptional.get();
             model.addAttribute("device", device);
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
        if (deviceService.update(edited) == null) {
            redirectAttributes.addAttribute("existUser", "false");
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
        if (deviceService.eisistById(appUtil.parseId(newDevice.getId()))) {
            redirectAttributes.addAttribute("existDevice", "thiết bị đã tồn tại");
            redirectAttributes.addFlashAttribute("device", newDevice);
            return "redirect:/admin/device/create";
        }
        deviceService.create(newDevice);
        redirectAttributes.addAttribute("success", "Tạo thiết bị thành công");
        return "redirect:/admin/device/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes
    ){
        if(deviceService.delete(id) == null){
            redirectAttributes.addAttribute("error", "not found");
            return "redirect:/admin/device/detail/" + id;
        }
        redirectAttributes.addAttribute("success", "Xoá thành công thiết bị " + id);
        return "redirect:/admin/device";
    }

    @GetMapping("/detail/usage/{id}")
    public String createUsage(
            @PathVariable("id") Long id,
            @ModelAttribute("borrow") BorrowRequestDto request,
            Model model
    ){
        Optional<DeviceResponseDto> deviceOptional = Optional.ofNullable(deviceService.findById(id));
        if (deviceOptional.isPresent()) {
            DeviceResponseDto device = deviceOptional.get();
            request.setDeviceId(device.getId());
            request.setDeviceName(device.getName());
        }
        return "device/create_usage";
    }

    @PostMapping("/detail/usage")
    public String createUsage(
            @ModelAttribute("borrow") BorrowRequestDto request,
            RedirectAttributes redirectAttributes
    ){
        if (personService.findByIdAndStatusTrue(request.getUserId()) == null) {
            redirectAttributes.addAttribute("userNotFound", true);
            return "redirect:/admin/device/detail/usage/" + request.getDeviceId();
        }
        if (usageInfoService.existByBookingTime(LocalDateTime.now(), request.getDeviceId()) ||
                usageInfoService.existByBorrowTime(LocalDateTime.now(), request.getDeviceId())) {
            redirectAttributes.addAttribute("failure", true);
            return "redirect:/admin/device/detail/usage/" + request.getDeviceId();
        }
        if (penalizeService.isPenalize(request.getUserId())) {
            redirectAttributes.addAttribute("penalize", true);
            return "redirect:/admin/device/detail/usage/" + request.getDeviceId();
        }
        UsageInfoBorrowDto result = usageInfoService.borrowDevice(request.getUserId(), request.getDeviceId());
        return "redirect:/admin/device/detail/usage/success/" + result.getId();
    }

    @GetMapping("/detail/usage/success/{id}")
    public String createUsageSuccess(
            @PathVariable("id") Long id,
            Model m
    ){
        UsageInfoBorrowDto usageInfoBorrowDto = usageInfoService.getBorrowById(id);
        System.out.println(appUtil.toJson(usageInfoBorrowDto));
        m.addAttribute("borrow", usageInfoBorrowDto);
        return "device/usage_success";
    }

    @PostMapping("/excel")
    @ResponseBody
    public ResponseEntity<String> importFile(@ModelAttribute("file") MultipartFile file) {
        Boolean result = personService.importFromExcel(file);
        if(result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failure");
        }
    }
}