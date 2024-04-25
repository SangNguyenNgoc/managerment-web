package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.entities.DeviceEntity;

import java.time.LocalDate;
import java.util.List;

public interface IDeviceService {
    boolean checkUseAndBooking(DeviceEntity device, LocalDate date);
    List<DeviceResponseDto> getAllByName(String name);
}
