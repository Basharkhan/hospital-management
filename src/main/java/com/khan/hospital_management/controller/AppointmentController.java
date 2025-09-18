package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        AppointmentDto appointment = appointmentService.createAppointment(request);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment created successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> updateAppointment(@PathVariable Long id,
                                                                         @Valid @RequestBody AppointmentUpdateRequest request) {
        AppointmentDto appointment = appointmentService.updateAppointment(id, request);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment updated successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentById(@PathVariable Long id) {
        AppointmentDto appointment = appointmentService.getAppointmentById(id);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment retrieved successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> confirmAppointment(@PathVariable Long id) {
        var appointment = appointmentService.confirmAppointment(id);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment confirmed successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> cancelAppointment(@PathVariable Long id) {
        var appointment = appointmentService.cancelAppointment(id);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment cancelled successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDto>> completeAppointment(@PathVariable Long id) {
        var appointment = appointmentService.completeAppointment(id);

        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Appointment completed successfully",
                appointment,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
