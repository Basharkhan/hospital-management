package com.khan.hospital_management.dto;

import lombok.Data;

@Data
public class DoctorRequest {
    private String specialization;
    private String phone;
    private String department;
    private String qualification;
    private Long userId;
}
