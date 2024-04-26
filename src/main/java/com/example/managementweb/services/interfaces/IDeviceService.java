package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;

import java.util.List;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.entities.DeviceEntity;

import java.time.LocalDate;
import java.util.List;

public interface IDeviceService {

        List<DeviceResponseDto> findAll();

        DeviceResponseDto findById(Long id);

        DeviceResponseDto create(DeviceCreateDto deviceCreateDto);

        DeviceResponseDto update(DeviceUpdateDto deviceUpdateDto);
        DeviceResponseDto delete(Long id);
        boolean checkUseAndBooking(DeviceEntity device, LocalDate date);
        List<DeviceResponseDto> getAllByName(String name);
}
