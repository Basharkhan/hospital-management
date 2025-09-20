package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BillDto {
    private Long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private Long patientId;
    private Long appointmentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
