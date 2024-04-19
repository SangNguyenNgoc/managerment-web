package com.example.managementweb.services;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.mappers.PersonMapper;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.services.interfaces.IPersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonService implements IPersonService {

    PersonRepository personRepository;

    PersonMapper personMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public List<PersonResponseDto> findAll() {
        List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonResponseDto findById(Long id) {
        PersonEntity person = personRepository.findById(id).orElse(null);
        return personMapper.toResponseDto(person);
    }

    @Override
    public PersonResponseDto create(PersonCreateDto personCreateDto) {
        PersonEntity personEntity = personMapper.toEntity(personCreateDto);
        personEntity.setPassword(passwordEncoder.encode(personEntity.getPassword()));
        personEntity.setStatus(true);
        personEntity.setRole(Role.ROLE_USER);
        PersonEntity result = personRepository.save(personEntity);
        return personMapper.toResponseDto(result);
    }

    @Override
    public PersonResponseDto update(PersonUpdateDto personUpdateDto) {
        PersonEntity person = personRepository.findById(Long.valueOf(personUpdateDto.getId())).orElse(null);
        if(person != null) {
            PersonEntity personAfterUpdate = personMapper.partialUpdate(personUpdateDto, person);
            PersonEntity result = personRepository.save(personAfterUpdate);
            return personMapper.toResponseDto(result);
        }
        return null;
    }
    @Override
    public PersonResponseDto delete(Long id) {
        PersonEntity person = personRepository.findById(Long.valueOf(id)).orElse(null);
        if(person != null) {
            person.setStatus(false);
            personRepository.save(person);
            return personMapper.toResponseDto(person);
        }
        return null;
    }
    public void addPersonsFromExcel(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề

                // Đọc dữ liệu từ các ô trong dòng
                String id = getStringValueFromCell(row.getCell(0));
                String name = getStringValueFromCell(row.getCell(1));
                String department = getStringValueFromCell(row.getCell(2));
                String profession = getStringValueFromCell(row.getCell(3));
                String phoneNumber = getStringValueFromCell(row.getCell(4));
                String password = getStringValueFromCell(row.getCell(5));
                String email = getStringValueFromCell(row.getCell(6));

                if (id.contains(".")) {
                    id = id.substring(0, id.indexOf("."));
                }

                PersonCreateDto personCreateDto = PersonCreateDto.builder()
                        .id(id)
                        .name(name)
                        .department(department)
                        .profession(profession)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .password(password)
                        .build();

                create(personCreateDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return null;
        }
    }
}
