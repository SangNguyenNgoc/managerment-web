package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    List<DeviceEntity> findByStatusTrue();

    DeviceEntity findByIdAndStatusTrue(Long id);

    boolean existsByIdAndStatusTrue(Long id);


}
