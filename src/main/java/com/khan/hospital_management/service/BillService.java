package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.BillDto;
import com.khan.hospital_management.dto.BillRequest;
import com.khan.hospital_management.exception.InvalidAppointmentException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Appointment;
import com.khan.hospital_management.model.Bill;
import com.khan.hospital_management.model.Patient;
import com.khan.hospital_management.model.PaymentStatus;
import com.khan.hospital_management.repository.AppointmentRepository;
import com.khan.hospital_management.repository.BillRepository;
import com.khan.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public BillDto createBill(BillRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + request.getAppointmentId()));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
           throw new InvalidAppointmentException("Appointment patient does not match with the patient id provided");
        }

        Bill bill = Bill.builder()
                .amount(request.getAmount())
                .status(PaymentStatus.PAID)
                .patient(patient)
                .appointment(appointment)
                .build();

        Bill savedBill = billRepository.save(bill);
        return mapToDto(savedBill);
    }

    @Transactional(readOnly = true)
    public BillDto getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
        return mapToDto(bill);
    }

    @Transactional(readOnly = true)
   public Page<BillDto> getAllBills(Pageable pageable) {
        return billRepository.findAll(pageable).map(this::mapToDto);
   }

    private BillDto mapToDto(Bill bill) {
        return BillDto.builder()
                .id(bill.getId())
                .amount(bill.getAmount())
                .status(bill.getStatus())
                .patientId(bill.getPatient().getId())
                .appointmentId(bill.getAppointment().getId())
                .createdAt(bill.getCreatedAt())
                .updatedAt(bill.getUpdatedAt())
                .build();
    }
}
