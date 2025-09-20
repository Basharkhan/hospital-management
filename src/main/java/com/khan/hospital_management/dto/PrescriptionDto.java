package com.khan.hospital_management.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PrescriptionDto {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private Long appointmentId;
    private List<PrescriptionItemDto> items;
}
