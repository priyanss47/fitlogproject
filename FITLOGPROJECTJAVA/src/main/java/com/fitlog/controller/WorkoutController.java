package com.fitlog.controller;

import com.fitlog.dto.WorkoutDTO;
import com.fitlog.entity.Workout;
import com.fitlog.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    // POST /api/workouts — Log a workout
    @PostMapping
    public ResponseEntity<Workout> logWorkout(@Valid @RequestBody WorkoutDTO dto) {
        Workout created = workoutService.logWorkout(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/workouts — Get all workouts
    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    // GET /api/workouts/{id} — Get workout by ID
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    // GET /api/workouts/user/{userId}?date=2024-01-15 — Get workouts by user and date
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserAndDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserAndDate(userId, date));
    }

    // GET /api/workouts/user/{userId}/week?startDate=2024-01-15&endDate=2024-01-21
    @GetMapping("/user/{userId}/week")
    public ResponseEntity<List<Workout>> getWorkoutsByWeek(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserAndWeek(userId, startDate, endDate));
    }

    // DELETE /api/workouts/{id} — Delete workout
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
