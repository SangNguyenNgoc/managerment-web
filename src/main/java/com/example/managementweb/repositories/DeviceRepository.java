package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    @Query("SELECT d " +
            "FROM DeviceEntity d " +
            "WHERE d.id = :id and d.status = true ")
    Optional<DeviceEntity> findByIdIsTrue(Long id);

    @Query("SELECT d " +
            "FROM DeviceEntity d " +
            "WHERE d.name LIKE CONCAT('%', :name, '%')and d.status = true ")
    List<DeviceEntity> findByName(String name);
}