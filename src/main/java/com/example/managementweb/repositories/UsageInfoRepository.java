package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.UsageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageInfoRepository extends JpaRepository<UsageInfoEntity, Long> {
}
