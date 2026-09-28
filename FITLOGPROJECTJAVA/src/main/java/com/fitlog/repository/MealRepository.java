package com.fitlog.repository;

import com.fitlog.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByUserIdAndDate(Long userId, LocalDate date);

    List<Meal> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(m.calories) FROM Meal m WHERE m.user.id = :userId AND m.date = :date")
    Double sumCaloriesByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
