package com.khan.hospital_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionItemDto {
    private Long id;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;
}
