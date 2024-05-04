package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.person.PersonAddDto;
import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.dtos.person.*;
import com.example.managementweb.models.entities.PersonEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IPersonService {

    List<PersonResponseDto> findAll();

    List<PersonResponseDto> findByStatusTrue();

    PersonResponseDto findById(Long id);

    PersonResponseDto findByIdAndStatusTrue(Long id);

    PersonAndPenaltiesDto getPersonAndPenalizeById(Long id, LocalDate date);

    PersonAndUsageDto getPersonAndUsageById(Long id, LocalDate date);

    PersonUpdateDto findByIdAndStatusTrueToUpdate(Long id);

    PersonResponseDto create(PersonCreateDto personCreateDto);

    PersonResponseDto register(PersonCreateDto personCreateDto);

    PersonResponseDto update(PersonUpdateDto personUpdateDto);

    PersonResponseDto delete(Long id);

    Boolean existById(Long id);

    Boolean existByIdAndStatusTrue(Long id);

    Boolean existByEmail(String email);

    Boolean importFromExcel(MultipartFile file);

    UserDetails createUserDetailFromRegister(PersonEntity person);

    void resetPassword(Long id);

    void sendOtp(Long userId, String email);

    boolean checkOtp(Long userId, String otp);

    boolean checkPassword(Long userId, String password);

    boolean checkConfirmPassword(String password, String confirmPassword);

    void changeEmail(Long userId);

    void changePassword(Long userId, String newPassword);
}
