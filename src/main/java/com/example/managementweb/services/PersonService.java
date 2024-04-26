package com.example.managementweb.services;

import com.example.managementweb.models.dtos.person.PersonCreateDto;
import com.example.managementweb.models.dtos.person.PersonResponseDto;
import com.example.managementweb.models.dtos.person.PersonUpdateDto;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.models.entities.Role;
import com.example.managementweb.services.mappers.PersonMapper;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.services.interfaces.IPersonService;
import com.example.managementweb.util.ObjectsValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.apache.poi.ss.usermodel.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonService implements IPersonService {

    PersonRepository personRepository;

    PersonMapper personMapper;

    PasswordEncoder passwordEncoder;

    ObjectsValidator<PersonCreateDto> personValidator;

    @Override
    public List<PersonResponseDto> findAll() {
        List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonResponseDto> findByStatusTrue() {
        List<PersonEntity> personEntities = personRepository.findByStatusTrue();
        return personEntities.stream()
                .map(personMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonResponseDto findById(Long id) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);
        return personEntity
                .map(personMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public PersonResponseDto findByIdAndStatusTrue(Long id) {
        Optional<PersonEntity> personEntity = personRepository.findByIdAndStatusTrue(id);
        return personEntity
                .map(personMapper::toResponseDto)
                .orElse(null);
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
        Optional<PersonEntity> personEntity = personRepository.findByIdAndStatusTrue(Long.valueOf(personUpdateDto.getId()));
        return personEntity
                .map(person -> {
                    PersonEntity personAfterUpdate = personMapper.partialUpdate(personUpdateDto, person);
                    PersonEntity result = personRepository.save(personAfterUpdate);
                    return personMapper.toResponseDto(result);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public PersonResponseDto delete(Long id) {
        Optional<PersonEntity> personEntity = personRepository.findByIdAndStatusTrue(id);
        return personEntity
                .map(person -> {
                    person.setStatus(false);
                    return personMapper.toResponseDto(person);
                })
                .orElse(null);
    }

    @Override
    public Boolean existById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public Boolean existByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    @Override
    public Boolean existByIdAndStatusTrue(Long id) {
        return personRepository.existsByIdAndStatusTrue(id);
    }

    @Override
    public Boolean importFromExcel(MultipartFile selectedFile) {
        try (Workbook workbook = WorkbookFactory.create(selectedFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<PersonEntity> personEntities = new ArrayList<>();
            for (Row row : sheet) {
                if(row.getCell(0) == null) {
                    break;
                }
                if (row.getRowNum() > 0) {
                    PersonCreateDto person = getFromRow(row);
                    if (!personValidator.validate(person).isEmpty()) {
                        throw new RuntimeException();
                    } else {
                        personEntities.add(personMapper.toEntity(person));
                    }
                }
            }
            if(!personEntities.isEmpty()) {
                personRepository.saveAll(personEntities);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private PersonCreateDto getFromRow(Row row) {
        return PersonCreateDto.builder()
                .id(String.valueOf((long) row.getCell(0).getNumericCellValue()))
                .name(row.getCell(1).getStringCellValue())
                .email(row.getCell(6).getStringCellValue())
                .password(String.valueOf(row.getCell(5).getNumericCellValue()))
                .department(row.getCell(2).getStringCellValue())
                .profession(row.getCell(3).getStringCellValue())
                .phoneNumber(row.getCell(4).getStringCellValue())
                .build();
    }

    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Chuyển đổi số thành chuỗi
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                // Giải công thức và trả về giá trị kết quả
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    case NUMERIC:
                        return String.valueOf(cellValue.getNumberValue());
                    case STRING:
                        return cellValue.getStringValue();
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}
