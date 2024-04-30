package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.penalize.PenalizeCreateDto;
import com.example.managementweb.models.dtos.penalize.PenalizeResponseDto;
import com.example.managementweb.models.dtos.penalize.PenalizeUpdateDto;

import java.util.List;

public interface IPenalizeService {
    List<PenalizeResponseDto> findAll();

    PenalizeResponseDto findById(Long id);

    PenalizeResponseDto create(PenalizeCreateDto penalizeCreateDto);

    PenalizeResponseDto update(PenalizeUpdateDto penalizeUpdateDto);

    PenalizeResponseDto updateStatus(Long id);

    void delete(Long id);

    boolean isPenalize(Long personId);
}
