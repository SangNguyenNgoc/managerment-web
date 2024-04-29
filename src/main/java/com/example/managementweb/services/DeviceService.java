package com.example.managementweb.services;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.entities.DeviceEntity;
import com.example.managementweb.repositories.DeviceRepository;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.services.mappers.DeviceMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceService implements IDeviceService {
    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;

    @Override
    public List<DeviceResponseDto> findAll() {
        List<DeviceEntity> deviceEntities = deviceRepository.findAll();
        return deviceEntities.stream()
                .map(deviceMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceResponseDto findById(Long id) {
        Optional<DeviceEntity> deviceEntity = deviceRepository.findById(id);
        return deviceEntity.
                map(deviceMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public DeviceResponseDto create(DeviceCreateDto deviceCreateDto) {
        DeviceEntity deviceEntity = deviceMapper.toEntity(deviceCreateDto);
        deviceEntity.setStatus(true);
        DeviceEntity result = deviceRepository.save(deviceEntity);
        return deviceMapper.toResponseDto(result);
    }

    @Override
    public DeviceResponseDto update(DeviceUpdateDto deviceUpdateDto) {
        Optional<DeviceEntity> deviceEntity = deviceRepository.findById(Long.valueOf(deviceUpdateDto.getId()));
        return deviceEntity
                .map(device -> {
                    DeviceEntity deviceAfterUpdate = deviceMapper.partialUpdate(deviceUpdateDto, device);
                    DeviceEntity result = deviceRepository.save(deviceAfterUpdate);
                    return deviceMapper.toResponseDto(result);
                })
                .orElse(null);
    }


    @Override
    @Transactional
    public DeviceResponseDto delete(Long id) {
        Optional<DeviceEntity> deviceEntity = deviceRepository.findById(id);
        return deviceEntity
                .map(device -> {
                    device.setStatus(false);
                    return deviceMapper.toResponseDto(device);
                }).orElse(null);
    }


    @Override
    public boolean checkUseAndBooking(DeviceEntity device,LocalDate date ) {
        return device.getUsageInfos()
                .stream()
                .anyMatch(item -> item.getReturnTime() == null ||(item.getBookingTime() != null && !item.getBookingTime().toLocalDate().isEqual(date)));
    }


    @Override
    public List<DeviceResponseDto> getAllByName(String name) {
        List<DeviceEntity> deviceEntities = deviceRepository.findByName(name);
        return deviceEntities.stream()
                .map(deviceEntity -> {
                    DeviceResponseDto dto = deviceMapper.toResponseDto(deviceEntity);
                    dto.setIsBorrow(checkUseAndBooking(deviceEntity, LocalDate.now()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
