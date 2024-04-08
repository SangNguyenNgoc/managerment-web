package com.example.managementweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageInfoRepository extends JpaRepository<UsageInfoRepository, Integer> {
}
