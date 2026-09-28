package com.fitlog.controller;

import com.fitlog.dto.SummaryResponseDTO;
import com.fitlog.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    /**
     * GET /api/summary/daily/{userId}?date=2024-01-15
     * Returns daily calories burned vs consumed summary.
     */
    @GetMapping("/daily/{userId}")
    public ResponseEntity<SummaryResponseDTO> getDailySummary(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(summaryService.getDailySummary(userId, date));
    }

    /**
     * GET /api/summary/weekly/{userId}?weekStartDate=2024-01-15
     * Returns weekly workout trend and goal status.
     */
    @GetMapping("/weekly/{userId}")
    public ResponseEntity<SummaryResponseDTO> getWeeklySummary(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStartDate) {
        return ResponseEntity.ok(summaryService.getWeeklySummary(userId, weekStartDate));
    }
}
