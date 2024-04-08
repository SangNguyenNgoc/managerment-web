package com.example.managementweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenalizeRepository  extends JpaRepository<PenalizeRepository, Integer> {
}
