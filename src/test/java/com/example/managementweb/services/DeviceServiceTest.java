package com.example.managementweb.services;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

@SpringBootTest
@SpringJUnitWebConfig
public class DeviceServiceTest {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AppUtil appUtil;

    @Test
     void findAll() {
        List<DeviceResponseDto> result = deviceService.findAll();
        System.out.println(appUtil.toJson(result));

    }

    @Test
    void findById() {
        DeviceResponseDto result = deviceService.findById(122L);
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void create() {
        DeviceCreateDto deviceCreateDto = DeviceCreateDto.builder()
                .id("2")
                .name("Device 2")
                .description("Description 2")
                .build();
        DeviceResponseDto result = deviceService.create(deviceCreateDto);
        System.out.println(appUtil.toJson(result));
    }
    @Test
    void update() {
        DeviceResponseDto result = deviceService.update(DeviceUpdateDto.builder()
                .id("2")
                .name("Device 3ww")
                .description("Description 3")
                .build());
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void delete() {
        DeviceResponseDto result = deviceService.delete(1L);
        System.out.println(appUtil.toJson(result));
    }

    void testFindByName(){
        List<DeviceResponseDto> deviceResponseDtos = deviceService.getAllByName("Micro");
        System.out.println(appUtil.toJson(deviceResponseDtos));
    }
}