package com.fitlog.controller;

import com.fitlog.dto.GoalDTO;
import com.fitlog.entity.Goal;
import com.fitlog.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    // GET /api/goals — Get ALL goals
    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    // POST /api/goals — Set a weekly goal
    @PostMapping
    public ResponseEntity<Goal> setGoal(@Valid @RequestBody GoalDTO dto) {
        Goal created = goalService.setGoal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/goals/user/{userId} — Get all goals for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Goal>> getGoalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(goalService.getGoalsByUser(userId));
    }

    // GET /api/goals/{id} — Get goal by ID
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    // DELETE /api/goals/{id} — Delete goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
