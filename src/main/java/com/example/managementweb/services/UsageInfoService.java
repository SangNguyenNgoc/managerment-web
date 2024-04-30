package com.example.managementweb.services;

import com.example.managementweb.event.DeleteBookingTask;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;
import com.example.managementweb.models.entities.DeviceEntity;
import com.example.managementweb.models.entities.PersonEntity;
import com.example.managementweb.models.entities.UsageInfoEntity;
import com.example.managementweb.repositories.DeviceRepository;
import com.example.managementweb.repositories.PenalizeRepository;
import com.example.managementweb.repositories.PersonRepository;
import com.example.managementweb.repositories.UsageInfoRepository;
import com.example.managementweb.services.interfaces.IUsageInfoService;
import com.example.managementweb.services.mappers.UsageInfoMapper;
import com.example.managementweb.util.AppUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsageInfoService implements IUsageInfoService {
    UsageInfoRepository usageInfoRepository;
    PersonRepository personRepository;
    DeviceRepository deviceRepository;
    PenalizeRepository penalizeRepository;
    UsageInfoMapper usageInfoMapper;
    AppUtil appUtil;

    @Override
    @Transactional
    public UsageInfoBorrowDto borrowDevice(Long userId, Long deviceId) {
        PersonEntity person = personRepository.findById(userId).orElse(null);
        DeviceEntity device = deviceRepository.findByIdIsTrue(deviceId).orElse(null);
        if(person == null
                || device == null
                || !penalizeRepository.findByPersonIsPenalize(userId).isEmpty()){
            return null;
        }
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .person(person)
                .device(device)
                .borrowTime(LocalDateTime.now())
                .build();
        usageInfoRepository.save(usageInfo);
        return usageInfoMapper.toBorrowDto(usageInfo);
    }

    @Override
    public List<UsageInfoBorrowDto> getAllBorrow() {
        return usageInfoMapper.toDtoList(usageInfoRepository.findAllBorrow());
    }

    @Override
    @Transactional
    public UsageInfoBorrowDto returnDevice(String id) {
        UsageInfoEntity usageInfo = usageInfoRepository.findByIdForReturn(appUtil.parseId(id));
        if (usageInfo == null){
            return null;
        }
        usageInfo.setReturnTime(LocalDateTime.now());
        return usageInfoMapper.toBorrowDto(usageInfo);
    }

    @Override
    @Transactional
    public Boolean deleteBorrow(Long id) {
        UsageInfoEntity usageInfo = usageInfoRepository.findByIdAndBorrow(id);
        if (usageInfo == null){
            return false;
        }
        usageInfoRepository.delete(usageInfo);
        return true;
    }

    @Override
    @Transactional
    public UsageInfoBookingDto bookingDevice(UsageInfoBookingRequestDto requestDto) {
        //Thêm exception
        PersonEntity person = personRepository.findById(requestDto.getPersonId()).orElse(null);
        DeviceEntity device = deviceRepository.findById(requestDto.getDeviceId()).orElse(null);

        UsageInfoEntity newUsageInfo = UsageInfoEntity.builder()
                .bookingTime(requestDto.getBookingTime())
                .device(device)
                .person(person)
                .build();
        UsageInfoEntity savedUsageInfo = usageInfoRepository.save(newUsageInfo);
        UsageInfoBookingDto usageInfoBookingDto = usageInfoMapper.toBookingDto(savedUsageInfo);
        //Hẹn giờ xóa
        LocalDateTime now = LocalDateTime.now();
        Timer timer = new Timer();
        timer.schedule(new DeleteBookingTask(usageInfoBookingDto.getId(), usageInfoRepository), Duration.between(now, requestDto.getBookingTime()).toMillis());
        return usageInfoBookingDto;
    }

    @Override
    public Boolean checkIn(Long personId) {
        PersonEntity person = personRepository.findById(personId).orElse(null);
        if(person == null || !penalizeRepository.findByPersonIsPenalize(personId).isEmpty()) {
            return false;
        }
        UsageInfoEntity usageInfo = usageInfoRepository.save(UsageInfoEntity.builder()
                .person(person)
                .checkinTime(LocalDateTime.now())
                .build());
        return true;
    }

    @Override
    public UsageInfoBookingDto getById(Long id) {
        Optional<UsageInfoEntity> usageInfo = usageInfoRepository.findById(id);
        return usageInfo
                .map(usageInfoMapper::toBookingDto)
                .orElse(null);
    }


    @Override
    public boolean existByBookingTime(LocalDateTime time, Long deviceId) {
        return usageInfoRepository.existsByBookingTime(time.toLocalDate(), deviceId);
    }

    @Override
    public boolean existByBorrowTime(LocalDateTime time, Long deviceId) {
        return usageInfoRepository.existsByBorrowTime(time.toLocalDate(), deviceId);
    }
}
