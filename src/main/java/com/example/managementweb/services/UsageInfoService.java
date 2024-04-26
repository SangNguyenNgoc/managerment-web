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

    @Override
    @Transactional
    public UsageInfoBorrowDto borrowDevice(String userId, String deviceId) {
        PersonEntity person = personRepository.findById(AppUtil.parseId(userId)).orElse(null);
        DeviceEntity device = deviceRepository.findByIdIsTrue(AppUtil.parseId(deviceId)).orElse(null);
        if(person == null
                || device == null
                || !penalizeRepository.findByPersonIsPenalize(AppUtil.parseId(userId)).isEmpty()){
            return null;
        }
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .person(person)
                .device(device)
                .borrowTime(LocalDateTime.now())
                .build();
        return coverToBorrowDto(usageInfoRepository.save(usageInfo));
    }

    @Override
    public List<UsageInfoBorrowDto> getAllBorrow() {
        return usageInfoMapper.toDtoList(usageInfoRepository.findAllBorrow());
    }

    @Override
    @Transactional
    public UsageInfoBorrowDto returnDevice(String id) {
        UsageInfoEntity usageInfo = usageInfoRepository.findByIdForReturn(AppUtil.parseId(id));
        if (usageInfo == null){
            return null;
        }
        usageInfo.setReturnTime(LocalDateTime.now());
        return coverToBorrowDto(usageInfo);
    }

    @Override
    @Transactional
    public Boolean deleteBorrow(String id) {
        UsageInfoEntity usageInfo = usageInfoRepository.findByIdAndBorrow(AppUtil.parseId(id));
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

        UsageInfoEntity newUsageInfo = usageInfoMapper.toEntity(requestDto);
        newUsageInfo.setPerson(person);
        newUsageInfo.setDevice(device);
        UsageInfoBookingDto usageInfoBookingDto = usageInfoMapper.toBookingDto(usageInfoRepository.save(newUsageInfo));
        usageInfoBookingDto.setBookingTime(AppUtil.dateToString(requestDto.getBookingTime()));
        //Hẹn giờ xóa
        LocalDateTime now = LocalDateTime.now();
        Timer timer = new Timer();
        timer.schedule(new DeleteBookingTask(usageInfoBookingDto.getId(), usageInfoRepository), Duration.between(now, requestDto.getBookingTime()).toMillis());

        return usageInfoBookingDto;
    }

    private UsageInfoBorrowDto coverToBorrowDto(UsageInfoEntity usageInfo){
        if(usageInfo == null){
            return null;
        }
        UsageInfoBorrowDto newUsageInfoBorrow = usageInfoMapper.toBorrowDto(usageInfo);
        newUsageInfoBorrow.setBorrowTime(AppUtil.dateToString(usageInfo.getBorrowTime()));
        newUsageInfoBorrow.setReturnTime(AppUtil.dateToString(usageInfo.getReturnTime()));
        return newUsageInfoBorrow;
    }
}
