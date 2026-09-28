package com.fitlog.service;

import com.fitlog.dto.GoalDTO;
import com.fitlog.entity.Goal;
import com.fitlog.entity.User;
import com.fitlog.exception.InvalidRequestException;
import com.fitlog.exception.ResourceNotFoundException;
import com.fitlog.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserService userService;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal setGoal(GoalDTO dto) {
        User user = userService.getUserById(dto.getUserId());

        // Business rule: only one goal per user per week
        if (goalRepository.existsByUserIdAndWeekStartDate(dto.getUserId(), dto.getWeekStartDate())) {
            throw new InvalidRequestException(
                    "A goal already exists for user " + dto.getUserId()
                    + " for the week starting " + dto.getWeekStartDate()
                    + ". Delete the existing goal first or update it.");
        }

        Goal goal = Goal.builder()
                .weekStartDate(dto.getWeekStartDate())
                .weeklyWorkoutCountGoal(dto.getWeeklyWorkoutCountGoal())
                .user(user)
                .build();

        return goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUser(Long userId) {
        userService.getUserById(userId);
        return goalRepository.findByUserId(userId);
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
    }

    public void deleteGoal(Long id) {
        getGoalById(id);
        goalRepository.deleteById(id);
    }
}
