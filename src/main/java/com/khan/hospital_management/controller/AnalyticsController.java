package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.ApiResponse;
import com.khan.hospital_management.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<Long>> getAppointmentsCount(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        var count = analyticsService.getAppointmentsCount(start, end);

        ApiResponse<Long> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appoints count retrieved successfully",
                count,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patients")
    public ResponseEntity<ApiResponse<Long>> getPatientCount(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        var count = analyticsService.getPatientCount(start, end);

        ApiResponse<Long> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patients count retrieved successfully",
                count,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<BigDecimal>> getRevenue(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        var count = analyticsService.getRevenue(start, end);

        ApiResponse<BigDecimal> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Revenue retrieved successfully",
                count,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
