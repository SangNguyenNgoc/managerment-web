package com.example.managementweb.services;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitWebConfig
class DeviceServiceTest {
    @Autowired
    DeviceService deviceService;

    @Autowired
    private AppUtil appUtil;

    @Test
    void testFindByName(){
        List<DeviceResponseDto> deviceResponseDtos = deviceService.getAllByName("Micro");
        System.out.println(appUtil.toJson(deviceResponseDtos));
    }
}