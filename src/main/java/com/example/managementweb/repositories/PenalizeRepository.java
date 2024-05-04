package com.example.managementweb.repositories;

import com.example.managementweb.models.dtos.statistic.CountPerDate;
import com.example.managementweb.models.entities.PenalizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenalizeRepository  extends JpaRepository<PenalizeEntity, Long> {
    @Query("SELECT p " +
            "FROM PenalizeEntity p " +
            "WHERE p.person.id = :id and p.status = true ")
    List<PenalizeEntity> findByPersonIsPenalize(Long id);

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(p.date, COUNT(p.id)) " +
            "FROM PenalizeEntity p " +
            "WHERE MONTH (p.date) = :month " +
            "AND YEAR (p.date) = :year " +
            "AND p.status = true " +
            "GROUP BY DATE(p.date) " +
            "ORDER BY p.date DESC")
    List<CountPerDate> countPerMonthNotPresent(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(p.date, COUNT(p.id)) " +
            "FROM PenalizeEntity p " +
            "WHERE MONTH (p.date) = :month " +
            "AND YEAR (p.date) = :year " +
            "AND p.status = false " +
            "GROUP BY DATE(p.date) " +
            "ORDER BY p.date DESC")
    List<CountPerDate> countPerMonthIsPresent(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(p.date, COUNT(p.id)) " +
            "FROM PenalizeEntity p " +
            "WHERE YEAR (p.date) = :year " +
            "AND p.status = true " +
            "GROUP BY MONTH (p.date) " +
            "ORDER BY p.date DESC")
    List<CountPerDate> countPerYearNotPresent(
            @Param("year") int year
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(p.date, COUNT(p.id)) " +
            "FROM PenalizeEntity p " +
            "WHERE YEAR (p.date) = :year " +
            "AND p.status = false " +
            "GROUP BY MONTH (p.date) " +
            "ORDER BY p.date DESC")
    List<CountPerDate> countPerYearIsPresent(
            @Param("year") int year
    );

}
