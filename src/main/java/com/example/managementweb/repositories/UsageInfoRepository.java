package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.DeviceEntity;
import com.example.managementweb.models.entities.UsageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsageInfoRepository extends JpaRepository<UsageInfoEntity, Long> {
    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.borrowTime IS NOT NULL")
    List<UsageInfoEntity> findAllBorrow();

    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.id = :id " +
            "AND u.returnTime IS NULL")
    UsageInfoEntity findByIdForReturn(Long id);

    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.id = :id " +
            "AND u.borrowTime IS NOT NULL")
    UsageInfoEntity findByIdAndBorrow(Long id);
}
