package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;
import com.example.managementweb.models.entities.UsageInfoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsageInfoMapper {
    UsageInfoEntity toEntity(UsageInfoBorrowDto usageInfoBorrowDto);

    UsageInfoBorrowDto toBorrowDto(UsageInfoEntity usageInfoEntity);

    List<UsageInfoBorrowDto> toDtoList(List<UsageInfoEntity> usageInfoEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBorrowDto usageInfoBorrowDto, @MappingTarget UsageInfoEntity usageInfoEntity);

    UsageInfoEntity toEntity(UsageInfoBookingDto usageInfoBookingDto);

    UsageInfoBookingDto toBookingDto(UsageInfoEntity usageInfoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBookingDto usageInfoBookingDto, @MappingTarget UsageInfoEntity usageInfoEntity);

    UsageInfoEntity toEntity(UsageInfoBookingRequestDto usageInfoBookingRequestDto);

    UsageInfoBookingRequestDto toBookingRequestDto(UsageInfoEntity usageInfoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBookingRequestDto usageInfoBookingRequestDto, @MappingTarget UsageInfoEntity usageInfoEntity);
}
