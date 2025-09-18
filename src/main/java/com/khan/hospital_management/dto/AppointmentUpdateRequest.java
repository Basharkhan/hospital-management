package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentUpdateRequest {
    @NotNull(message = "Appoint date is required")
    private LocalDateTime appointmentDate;
    private String reason;
}
