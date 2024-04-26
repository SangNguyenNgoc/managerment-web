package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository  extends JpaRepository<PersonEntity, Long> {

    boolean existsByEmail(String email);

    List<PersonEntity> findByStatusTrue();

    Optional<PersonEntity> findByIdAndStatusTrue(Long id);

    boolean existsByIdAndStatusTrue(Long id);
}
