package com.example.managementweb.services.mappers;

import com.example.managementweb.models.dtos.person.*;
import com.example.managementweb.models.entities.PenalizeEntity;
import com.example.managementweb.models.entities.PersonEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface PersonMapper {

    PersonEntity toEntity(PersonCreateDto personCreateDTO);

    PersonResponseDto toResponseDto(PersonEntity personEntity);

    PersonUpdateDto toUpdateDto(PersonEntity personEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonEntity partialUpdate(PersonUpdateDto personUpdateDto, @MappingTarget PersonEntity personEntity);

    PenalizeEntity toEntity(PenalizeEntity penalizeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PenalizeEntity partialUpdate(PenalizeEntity penalizeDto, @MappingTarget PenalizeEntity penalizeEntity);

    @AfterMapping
    default void linkPenalties(@MappingTarget PersonEntity personEntity) {
        personEntity.getPenalties().forEach(penalty -> penalty.setPerson(personEntity));
    }

    @Mappings({
            @Mapping(target = "penalties", source = "penalties"),
    })
    PersonAndPenaltiesDto toDto(PersonEntity personEntity);

    @AfterMapping
    default void mapPenaltyDates(@MappingTarget PersonAndPenaltiesDto dto) {
        dto.getPenalties().forEach(penalizeDto -> {
            LocalDateTime dateTime = LocalDateTime.parse(penalizeDto.getDate());
            penalizeDto.setDate(dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        });
        List<PersonAndPenaltiesDto.PenalizeDto> afterSort = dto.getPenalties().stream()
                .sorted(Comparator.comparing(PersonAndPenaltiesDto.PenalizeDto::getId))
                .collect(Collectors.toList());
        dto.setPenalties(afterSort);
    }


    @AfterMapping
    default void linkUsageInfos(@MappingTarget PersonEntity personEntity) {
        personEntity.getUsageInfos().forEach(usageInfo -> usageInfo.setPerson(personEntity));
    }

    PersonAndUsageDto toPersonAndUsage(PersonEntity personEntity);

    @AfterMapping
    default void mapUsageDates(@MappingTarget PersonAndUsageDto dto) {
        dto.getUsageInfos().forEach(usageInfoDto -> {
            if(usageInfoDto.getBookingTime() != null) {
                LocalDateTime bookingTime = LocalDateTime.parse(usageInfoDto.getBookingTime());
                usageInfoDto.setBookingTime(bookingTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
            if(usageInfoDto.getBorrowTime() != null) {
                LocalDateTime borrowTime = LocalDateTime.parse(usageInfoDto.getBorrowTime());
                usageInfoDto.setBorrowTime(borrowTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
            if(usageInfoDto.getReturnTime() != null) {
                LocalDateTime returnTime = LocalDateTime.parse(usageInfoDto.getReturnTime());
                usageInfoDto.setReturnTime(returnTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
        });
        List<PersonAndUsageDto.UsageInfoDto> afterSort = dto.getUsageInfos().stream()
                .sorted(Comparator.comparing(PersonAndUsageDto.UsageInfoDto::getId))
                .collect(Collectors.toList());
        dto.setUsageInfos(afterSort);
    }

    PersonEntity toEntity(PersonAddDto personAddDto);

    PersonAddDto toDto(PersonEntity personEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonEntity partialUpdate(PersonAddDto personAddDto, @MappingTarget PersonEntity personEntity);
}
