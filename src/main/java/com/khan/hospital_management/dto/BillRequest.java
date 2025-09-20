package com.khan.hospital_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequest {
    @NotNull(message = "Appointment is required")
    private Long appointmentId;

    @NotNull(message = "Patient id is required")
    private Long patientId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
