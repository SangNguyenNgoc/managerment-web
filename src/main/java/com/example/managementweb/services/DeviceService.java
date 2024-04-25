package com.example.managementweb.services;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.entities.DeviceEntity;
import com.example.managementweb.repositories.DeviceRepository;
import com.example.managementweb.services.interfaces.IDeviceService;
import com.example.managementweb.services.mappers.DeviceMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceService implements IDeviceService {
    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;
    @Override
    public boolean checkUseAndBooking(DeviceEntity device,LocalDate date ) {
        return device.getUsageInfos()
                .stream()
                .anyMatch(item -> item.getReturnTime() == null ||(item.getBookingTime() != null && !item.getBookingTime().toLocalDate().isEqual(date)));
    }

    @Override
    public List<DeviceResponseDto> getAllByName(String name) {
        List<DeviceEntity> deviceEntities = deviceRepository.findByName(name);
        deviceEntities.stream()
                .filter(deviceEntity -> checkUseAndBooking(deviceEntity,LocalDate.now()))
                .toList();
        return deviceMapper.toResponseDtoList(deviceEntities);
    }
}
