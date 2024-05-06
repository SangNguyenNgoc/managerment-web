package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.entities.DeviceEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IDeviceService {

    List<DeviceResponseDto> findAll();

    List<DeviceResponseDto> findByStatusTrue();

    DeviceResponseDto findById(Long id);

    DeviceResponseDto findByIdAndStatusTrue(Long id);

    DeviceResponseDto create(DeviceCreateDto deviceCreateDto);

    DeviceResponseDto update(DeviceUpdateDto deviceUpdateDto);

    DeviceResponseDto delete(Long id);

    boolean checkUseAndBooking(DeviceEntity device, LocalDate date);

    List<DeviceResponseDto> getAllByName(String name);

    Boolean eisistById(Long id);

    Boolean importFromExcel(MultipartFile selectedFile);
}
