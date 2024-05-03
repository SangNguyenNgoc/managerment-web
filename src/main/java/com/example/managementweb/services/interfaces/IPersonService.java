package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.person.PersonAddDto;
import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PersonEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPersonService {

    List<PersonResponseDto> findAll();

    List<PersonResponseDto> findByStatusTrue();

    PersonResponseDto findById(Long id);

    PersonResponseDto findByIdAndStatusTrue(Long id);

    PersonResponseDto create(PersonAddDto personAddDto);

    PersonResponseDto register(PersonCreateDto personCreateDto);

    PersonResponseDto update(PersonUpdateDto personUpdateDto);

    PersonResponseDto delete(Long id);

    Boolean existById(Long id);

    Boolean existByIdAndStatusTrue(Long id);

    Boolean existByEmail(String email);

    Boolean importFromExcel(MultipartFile file);

    UserDetails createUserDetailFromRegister(PersonEntity person);

    void resetPassword(Long id);
}
