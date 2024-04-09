package com.example.managementweb.repositories;

import com.example.managementweb.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository  extends JpaRepository<DeviceEntity, Integer> {
}
