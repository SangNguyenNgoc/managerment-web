package com.example.managementweb.event;

import com.example.managementweb.models.entities.UsageInfoEntity;
import com.example.managementweb.repositories.UsageInfoRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.TimerTask;

@RequiredArgsConstructor
public class DeleteBookingTask extends TimerTask {

    private final Long usageId;

    private final UsageInfoRepository usageInfoRepository;

    @Override
    public void run() {
        Optional<UsageInfoEntity> usageInfo = usageInfoRepository.findById(usageId);
        if (usageInfo.isPresent() && usageInfo.get().getBorrowTime() == null){
            usageInfoRepository.deleteById(usageId);
        }
        cancel();
    }
}
