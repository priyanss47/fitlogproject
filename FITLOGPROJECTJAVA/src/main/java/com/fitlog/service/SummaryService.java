package com.fitlog.service;

import com.fitlog.dto.SummaryResponseDTO;
import com.fitlog.entity.Goal;
import com.fitlog.exception.InvalidRequestException;
import com.fitlog.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final WorkoutService workoutService;
    private final MealService mealService;
    private final GoalRepository goalRepository;
    private final UserService userService;

    /**
     * Daily Summary: calories burned vs consumed for a given date.
     * Business rule: summary is only generated if at least one workout OR meal entry exists for that day.
     */
    public SummaryResponseDTO getDailySummary(Long userId, LocalDate date) {
        userService.getUserById(userId); // validate user exists

        double caloriesBurnt = workoutService.getDailyCaloriesBurnt(userId, date);
        double caloriesConsumed = mealService.getDailyCaloriesConsumed(userId, date);

        // Business rule: at least one entry must exist for that date
        if (caloriesBurnt == 0.0 && caloriesConsumed == 0.0) {
            throw new InvalidRequestException(
                    "No workout or meal entries found for user " + userId + " on " + date
                    + ". A daily summary requires at least one entry.");
        }

        return SummaryResponseDTO.builder()
                .date(date)
                .totalCaloriesBurnt(caloriesBurnt)
                .totalCaloriesConsumed(caloriesConsumed)
                .calorieBalance(caloriesConsumed - caloriesBurnt)
                .build();
    }

    /**
     * Weekly Summary: workout trend for a given week (start date).
     * Business rule: summary only generated if at least one workout exists that week.
     */
    public SummaryResponseDTO getWeeklySummary(Long userId, LocalDate weekStartDate) {
        userService.getUserById(userId); // validate user exists

        LocalDate weekEndDate = weekStartDate.plusDays(6);
        Long workoutCount = workoutService.getWeeklyWorkoutCount(userId, weekStartDate, weekEndDate);

        // Business rule: at least one workout must exist for that week
        if (workoutCount == 0) {
            throw new InvalidRequestException(
                    "No workout entries found for user " + userId
                    + " for the week of " + weekStartDate
                    + ". A weekly summary requires at least one workout entry.");
        }

        Optional<Goal> goalOpt = goalRepository.findByUserIdAndWeekStartDate(userId, weekStartDate);
        String goalStatus = "NO_GOAL_SET";
        Integer weeklyGoal = null;

        if (goalOpt.isPresent()) {
            weeklyGoal = goalOpt.get().getWeeklyWorkoutCountGoal();
            goalStatus = workoutCount >= weeklyGoal ? "ACHIEVED" : "IN_PROGRESS";
        }

        return SummaryResponseDTO.builder()
                .weekStartDate(weekStartDate)
                .weekEndDate(weekEndDate)
                .totalWorkoutsCompleted(workoutCount)
                .weeklyGoal(weeklyGoal)
                .goalStatus(goalStatus)
                .build();
    }
}
