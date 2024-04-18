package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.device.DeviceCreateDto;
import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.dtos.device.DeviceUpdateDto;
import com.example.managementweb.models.entities.DeviceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceEntity toEntity(DeviceCreateDto deviceCreateDto);
    DeviceResponseDto toResponseDto(DeviceEntity deviceEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeviceEntity partialUpdate(DeviceUpdateDto DeviceUpdateDto, @MappingTarget DeviceEntity deviceEntity);
}

