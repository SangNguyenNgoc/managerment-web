package com.example.managementweb.services;




import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;
import com.example.managementweb.models.entities.PenalizeEntity;
import com.example.managementweb.repositories.PenalizeRepository;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.services.mappers.PenalizeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PenalizeService implements  IPenalizeService{
    PenalizeRepository penalizeRepository;
    PenalizeMapper penalizeMapper;


@Override
    public List<PenalizeResponseDto> findAll() {
        List<PenalizeEntity> penalizeEntities = penalizeRepository.findAll();
        return penalizeEntities.stream()
                .map(penalizeMapper::toResponseDto)
                .collect(Collectors.toList());
    }
@Override
    public PenalizeResponseDto findById(Long id) {
        PenalizeEntity penalize = penalizeRepository.findById(Long.valueOf(id)).orElse(null);
        return penalizeMapper.toResponseDto(penalize);
    }
@Override
    public PenalizeResponseDto create(PenalizeCreateDto penalizeCreateDto) {
        PenalizeEntity penalizeEntity = penalizeMapper.toEntity(penalizeCreateDto);
        PenalizeEntity.setType(penalizeEntity.getType());
        PenalizeEntity.setPayment(penalizeEntity.getPayment());
        PenalizeEntity.setDate(LocalDateTime.now());
        PenalizeEntity.setStatus(false);
        PenalizeEntity result = penalizeRepository.save(penalizeEntity);
        return penalizeMapper.toResponseDto(result);

    }
    @Override
    public PenalizeResponseDto update(PenalizeUpdateDto penalizeUpdateDto) {
        PenalizeEntity penalize = penalizeRepository.findById(Long.valueOf(penalizeUpdateDto.getId())).orElse(null);
        if(penalize != null) {
            PenalizeEntity penalizeAfterUpdate = penalizeMapper.partialUpdate(penalizeUpdateDto, penalize);
            PenalizeEntity result = penalizeRepository.save(penalizeAfterUpdate);
            return penalizeMapper.toResponseDto(result);
        }
        return null;
    }
    @Override
    public PenalizeResponseDto delete(Long id) {
        PenalizeEntity penalize = penalizeRepository.findById(Long.valueOf(id)).orElse(null);
        if(penalize != null) {
            penalize.setStatus(true);
            penalizeRepository.save(penalize);
            return penalizeMapper.toResponseDto(penalize);
        }
        return null;
    }

}