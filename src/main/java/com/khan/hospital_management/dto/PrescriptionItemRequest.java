package com.khan.hospital_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionItemRequest {
    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;
}
