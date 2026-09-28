package com.fitlog.service;

import com.fitlog.dto.MealDTO;
import com.fitlog.entity.Meal;
import com.fitlog.entity.User;
import com.fitlog.exception.ResourceNotFoundException;
import com.fitlog.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final UserService userService;

    public Meal logMeal(MealDTO dto) {
        User user = userService.getUserById(dto.getUserId());

        // Business rule: calories must be non-negative (also enforced by @Min in DTO)
        if (dto.getCalories() < 0) {
            throw new IllegalArgumentException("Calories must be non-negative");
        }

        Meal meal = Meal.builder()
                .foodItem(dto.getFoodItem())
                .quantity(dto.getQuantity())
                .calories(dto.getCalories())
                .date(dto.getDate())
                .user(user)
                .build();

        return mealRepository.save(meal);
    }

    public List<Meal> getMealsByUserAndDate(Long userId, LocalDate date) {
        userService.getUserById(userId);
        return mealRepository.findByUserIdAndDate(userId, date);
    }

    public List<Meal> getMealsByUserAndWeek(Long userId, LocalDate startDate, LocalDate endDate) {
        userService.getUserById(userId);
        return mealRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));
    }

    public void deleteMeal(Long id) {
        getMealById(id);
        mealRepository.deleteById(id);
    }

    public Double getDailyCaloriesConsumed(Long userId, LocalDate date) {
        Double total = mealRepository.sumCaloriesByUserAndDate(userId, date);
        return total != null ? total : 0.0;
    }
}
