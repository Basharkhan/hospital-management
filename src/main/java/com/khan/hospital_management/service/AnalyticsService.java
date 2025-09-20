package com.khan.hospital_management.service;

import com.khan.hospital_management.model.PaymentStatus;
import com.khan.hospital_management.repository.AppointmentRepository;
import com.khan.hospital_management.repository.BillRepository;
import com.khan.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final BillRepository billRepository;

    public Long getAppointmentsCount(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.countByAppointmentDateBetween(start, end);
    }

    public Long getPatientCount(LocalDateTime start, LocalDateTime end) {
        return patientRepository.countByCreatedAtBetween(start, end);
    }

    public BigDecimal getRevenue(LocalDateTime start, LocalDateTime end) {
        return billRepository.sumAmountByCreatedAtBetweenAndStatus(start, end, PaymentStatus.PAID);
    }
}
