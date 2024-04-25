package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.device.DeviceResponseDto;
import com.example.managementweb.models.entities.DeviceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceEntity toEntity(DeviceResponseDto deviceResponseDto);

    DeviceResponseDto toDto(DeviceEntity deviceEntity);

    List<DeviceResponseDto> toResponseDtoList(List<DeviceEntity> deviceEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeviceEntity partialUpdate(DeviceResponseDto deviceResponseDto, @MappingTarget DeviceEntity deviceEntity);
}
