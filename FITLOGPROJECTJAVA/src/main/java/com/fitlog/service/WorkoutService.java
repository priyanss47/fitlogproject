package com.fitlog.service;

import com.fitlog.dto.WorkoutDTO;
import com.fitlog.entity.User;
import com.fitlog.entity.Workout;
import com.fitlog.exception.ResourceNotFoundException;
import com.fitlog.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserService userService;

    public Workout logWorkout(WorkoutDTO dto) {
        User user = userService.getUserById(dto.getUserId());

        // Business rule: calories must be non-negative (also enforced by @Min in DTO)
        if (dto.getCaloriesBurnt() < 0) {
            throw new IllegalArgumentException("Calories burnt must be non-negative");
        }

        Workout workout = Workout.builder()
                .type(dto.getType())
                .durationMins(dto.getDurationMins())
                .caloriesBurnt(dto.getCaloriesBurnt())
                .date(dto.getDate())
                .user(user)
                .build();

        return workoutRepository.save(workout);
    }

    public List<Workout> getWorkoutsByUserAndDate(Long userId, LocalDate date) {
        userService.getUserById(userId); // validate user exists
        return workoutRepository.findByUserIdAndDate(userId, date);
    }

    public List<Workout> getWorkoutsByUserAndWeek(Long userId, LocalDate startDate, LocalDate endDate) {
        userService.getUserById(userId);
        return workoutRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));
    }

    public void deleteWorkout(Long id) {
        getWorkoutById(id); // will throw if not found
        workoutRepository.deleteById(id);
    }

    public Double getDailyCaloriesBurnt(Long userId, LocalDate date) {
        Double total = workoutRepository.sumCaloriesBurntByUserAndDate(userId, date);
        return total != null ? total : 0.0;
    }

    public Long getWeeklyWorkoutCount(Long userId, LocalDate start, LocalDate end) {
        return workoutRepository.countWorkoutsByUserAndWeek(userId, start, end);
    }
}
