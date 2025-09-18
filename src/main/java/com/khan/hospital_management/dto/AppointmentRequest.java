package com.khan.hospital_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentRequest {
    @NotNull(message = "Appoint date is required")
    private LocalDateTime appointmentDate;
    private String reason;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;
}
