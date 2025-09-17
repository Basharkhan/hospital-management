package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentRequest {
    @NotBlank(message = "Appoint date is required")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Appoint status is required")
    private AppointmentStatus appointmentStatus;
    private String reason;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;
}
