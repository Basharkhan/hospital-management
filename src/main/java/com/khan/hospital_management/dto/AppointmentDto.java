package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDto {
    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus appointmentStatus;
    private String reason;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
}
