package com.khan.hospital_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorUpdateRequest {
    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Qualification is required")
    private String qualification;
}
