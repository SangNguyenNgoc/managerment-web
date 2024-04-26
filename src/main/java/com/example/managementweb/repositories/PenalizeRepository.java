package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.PenalizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenalizeRepository  extends JpaRepository<PenalizeEntity, Long> {
    @Query("SELECT p " +
            "FROM PenalizeEntity p " +
            "WHERE p.person.id = :id and p.status = true ")
    List<PenalizeEntity> findByPersonIsPenalize(Long id);
}
