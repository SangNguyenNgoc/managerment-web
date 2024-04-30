package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;
import com.example.managementweb.models.entities.UsageInfoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsageInfoMapper {
    UsageInfoEntity toEntity(UsageInfoBorrowDto usageInfoBorrowDto);

    @Mappings({
            @Mapping(target = "borrowTime", source = "borrowTime", dateFormat = "dd-MM-yyyy HH:mm"),
            @Mapping(target = "returnTime", source = "returnTime", dateFormat = "dd-MM-yyyy HH:mm")
    })
    UsageInfoBorrowDto toBorrowDto(UsageInfoEntity usageInfoEntity);

    List<UsageInfoBorrowDto> toDtoList(List<UsageInfoEntity> usageInfoEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBorrowDto usageInfoBorrowDto, @MappingTarget UsageInfoEntity usageInfoEntity);

    UsageInfoEntity toEntity(UsageInfoBookingDto usageInfoBookingDto);

    @Mappings({
            @Mapping(target = "bookingTime", source = "bookingTime", dateFormat = "dd-MM-yyyy HH:mm"),
            @Mapping(target = "borrowTime", source = "borrowTime", dateFormat = "dd-MM-yyyy HH:mm")

    })
    UsageInfoBookingDto toBookingDto(UsageInfoEntity usageInfoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBookingDto usageInfoBookingDto, @MappingTarget UsageInfoEntity usageInfoEntity);

    UsageInfoEntity toEntity(UsageInfoBookingRequestDto usageInfoBookingRequestDto);

    UsageInfoBookingRequestDto toBookingRequestDto(UsageInfoEntity usageInfoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsageInfoEntity partialUpdate(UsageInfoBookingRequestDto usageInfoBookingRequestDto, @MappingTarget UsageInfoEntity usageInfoEntity);
}
