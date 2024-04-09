package com.example.managementweb.repositories;

import com.example.managementweb.entities.PenalizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenalizeRepository  extends JpaRepository<PenalizeEntity, Integer> {
}
