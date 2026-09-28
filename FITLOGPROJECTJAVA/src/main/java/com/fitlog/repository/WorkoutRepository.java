package com.fitlog.repository;

import com.fitlog.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUserIdAndDate(Long userId, LocalDate date);

    List<Workout> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(w.caloriesBurnt) FROM Workout w WHERE w.user.id = :userId AND w.date = :date")
    Double sumCaloriesBurntByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(w) FROM Workout w WHERE w.user.id = :userId AND w.date BETWEEN :start AND :end")
    Long countWorkoutsByUserAndWeek(@Param("userId") Long userId,
                                    @Param("start") LocalDate start,
                                    @Param("end") LocalDate end);
}
