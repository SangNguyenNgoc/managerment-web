package com.example.managementweb.services;


import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;
import com.example.managementweb.models.entities.PenalizeEntity;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.repositories.PenalizeRepository;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.services.interfaces.IPenalizeService;
import com.example.managementweb.services.mappers.PenalizeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PenalizeService implements IPenalizeService {

    PenalizeRepository penalizeRepository;

    PenalizeMapper penalizeMapper;

    PersonRepository personRepository;

    @Override
    public List<PenalizeResponseDto> findAll() {
        return penalizeRepository.findAll().stream()
                .map(penalizeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PenalizeResponseDto findById(Long id) {
        Optional<PenalizeEntity> penalizeEntity = penalizeRepository.findById(id);
        return penalizeEntity.map(penalizeMapper::toDto).orElse(null);
    }

    @Override
    public PenalizeResponseDto create(PenalizeCreateDto penalizeCreateDto) {
        Optional<PersonEntity> personEntityOptional = personRepository
                .findByIdAndStatusTrue(penalizeCreateDto.getPerson());
        if (personEntityOptional.isPresent()) {
            PersonEntity personEntity = personEntityOptional.get();
            PenalizeEntity penalizeEntity = PenalizeEntity.builder()
                    .date(LocalDateTime.now())
                    .status(true)
                    .type(penalizeCreateDto.getType())
                    .person(personEntity)
                    .payment(penalizeCreateDto.getPayment())
                    .build();
            PenalizeEntity savedPenalize = penalizeRepository.save(penalizeEntity);
            return penalizeMapper.toDto(savedPenalize);
        } else {
            return null;
        }
    }

    @Override
    public PenalizeResponseDto update(PenalizeUpdateDto penalizeUpdateDto) {
        Optional<PenalizeEntity> penalizeEntity = penalizeRepository.findById(penalizeUpdateDto.getId());
        return penalizeEntity
                .map(penalize -> {
                    PenalizeEntity penalizeAfterUpdate = penalizeMapper.partialUpdate(penalizeUpdateDto, penalize);
                    PenalizeEntity result = penalizeRepository.save(penalizeAfterUpdate);
                    return penalizeMapper.toDto(result);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public PenalizeResponseDto updateStatus(Long id) {
        Optional<PenalizeEntity> penalizeEntity = penalizeRepository.findById(id);
        return penalizeEntity
                .map(penalize -> {
                    penalize.setStatus(!penalize.getStatus());
                    return penalizeMapper.toDto(penalize);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        penalizeRepository.deleteById(id);
    }

    @Override
    public boolean isPenalize(Long personId) {
        return !penalizeRepository.findByPersonIsPenalize(personId).isEmpty();
    }
}