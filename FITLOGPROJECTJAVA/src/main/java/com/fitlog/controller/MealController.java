package com.fitlog.controller;

import com.fitlog.dto.MealDTO;
import com.fitlog.entity.Meal;
import com.fitlog.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    // POST /api/meals — Log a meal
    @PostMapping
    public ResponseEntity<Meal> logMeal(@Valid @RequestBody MealDTO dto) {
        Meal created = mealService.logMeal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/meals — Get all meals
    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    // GET /api/meals/{id} — Get meal by ID
    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    // GET /api/meals/user/{userId}?date=2024-01-15 — Get meals by user and date
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Meal>> getMealsByUserAndDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.getMealsByUserAndDate(userId, date));
    }

    // GET /api/meals/user/{userId}/week?startDate=2024-01-15&endDate=2024-01-21
    @GetMapping("/user/{userId}/week")
    public ResponseEntity<List<Meal>> getMealsByWeek(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(mealService.getMealsByUserAndWeek(userId, startDate, endDate));
    }

    // DELETE /api/meals/{id} — Delete meal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
