package com.khan.hospital_management.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MedicalRecordDto {
    private Long id;
    private String diagnosis;
    private String treatment;
    private String notes;
    private Long doctorId;
    private Long patientId;
    private Long appointmentId;
}
