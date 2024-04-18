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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DeviceService implements IDeviceService {
    DeviceRepository deviceRepository ;
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
        DeviceEntity device = deviceRepository.findById(Long.valueOf(id)).orElse(null);
        return deviceMapper.toResponseDto(device);
    }

    @Override
    public DeviceResponseDto create(DeviceCreateDto deviceCreateDto) {
        DeviceEntity deviceEntity = deviceMapper.toEntity(deviceCreateDto);
        deviceEntity.setName(deviceEntity.getName());
        deviceEntity.setDescription(deviceEntity.getDescription());
        deviceEntity.setStatus(true);
        DeviceEntity result = deviceRepository.save(deviceEntity);
        return deviceMapper.toResponseDto(result);

    }

    @Override
    public DeviceResponseDto update(DeviceUpdateDto deviceUpdateDto) {
        DeviceEntity device = deviceRepository.findById(Long.valueOf(deviceUpdateDto.getId())).orElse(null);
        if(device != null) {
            DeviceEntity deviceAfterUpdate = deviceMapper.partialUpdate(deviceUpdateDto, device);
            DeviceEntity result = deviceRepository.save(deviceAfterUpdate);
            return deviceMapper.toResponseDto(result);
        }
        return null;
    }

//    @Override
//    public DeviceResponseDto delete(Long id) {
//        DeviceEntity device = deviceRepository.findById(Long.valueOf(id)).orElse(null);
//        if(device != null) {
//            deviceRepository.delete(device);
//            device.setStatus(false);
//            return deviceMapper.toResponseDto(device);
//        }
//        return null;
//    }
    @Override
    public DeviceResponseDto delete(Long id) {
        DeviceEntity device = deviceRepository.findById(Long.valueOf(id)).orElse(null);
        if(device != null) {
            device.setStatus(false);
            deviceRepository.save(device);
            return deviceMapper.toResponseDto(device);
        }
        return null;
    }
}
