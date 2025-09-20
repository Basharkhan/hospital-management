package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.PrescriptionDto;
import com.khan.hospital_management.dto.PrescriptionItemDto;
import com.khan.hospital_management.dto.PrescriptionRequest;
import com.khan.hospital_management.exception.InvalidAppointmentException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.*;
import com.khan.hospital_management.repository.AppointmentRepository;
import com.khan.hospital_management.repository.DoctorRepository;
import com.khan.hospital_management.repository.PatientRepository;
import com.khan.hospital_management.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionDto createPrescription(PrescriptionRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + request.getAppointmentId()));

        // Ensure appointment matches doctor & patient
        if (!appointment.getDoctor().getId().equals(doctor.getId()) ||
                !appointment.getPatient().getId().equals(patient.getId())) {
            throw new InvalidAppointmentException("Doctor or patient mismatch with appointment");
        }

        Prescription prescription = Prescription.builder()
                .doctor(doctor)
                .patient(patient)
                .appointment(appointment)
                .build();

        List<PrescriptionItem> items = request.getItems().stream()
                .map(req -> PrescriptionItem.builder()
                        .medicineName(req.getMedicineName())
                        .dosage(req.getDosage())
                        .duration(req.getDuration())
                        .frequency(req.getFrequency())
                        .instructions(req.getInstructions())
                        .prescription(prescription)
                        .build())
                .toList();

        prescription.setItems(items);

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return mapToDto(savedPrescription);
    }

    public PrescriptionDto getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
        return mapToDto(prescription);
    }

    public Page<PrescriptionDto> getPrescriptionsByPatientId(Long patientId, Pageable pageable) {
        Page<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId, pageable);
        return prescriptions.map(this::mapToDto);
    }

    public Page<PrescriptionDto> getPrescriptionsByDoctorId(Long doctorId, Pageable pageable) {
        Page<Prescription> prescriptions = prescriptionRepository.findByDoctorId(doctorId, pageable);
        return prescriptions.map(this::mapToDto);
    }

    private PrescriptionDto mapToDto(Prescription prescription) {
        PrescriptionDto prescriptionDto = PrescriptionDto.builder()
                .id(prescription.getId())
                .doctorId(prescription.getDoctor().getId())
                .patientId(prescription.getPatient().getId())
                .appointmentId(prescription.getAppointment().getId())
                .build();

        List<PrescriptionItemDto> items = prescription.getItems().stream()
                .map(item -> PrescriptionItemDto.builder()
                        .id(item.getId())
                        .medicineName(item.getMedicineName())
                        .dosage(item.getDosage())
                        .frequency(item.getFrequency())
                        .duration(item.getDuration())
                        .instructions(item.getInstructions())
                        .build())
                .toList();
        prescriptionDto.setItems(items);

        return prescriptionDto;
    }
}
