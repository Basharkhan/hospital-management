package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.MedicalRecordDto;
import com.khan.hospital_management.dto.MedicalRecordRequest;
import com.khan.hospital_management.exception.InvalidAppointmentException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.*;
import com.khan.hospital_management.repository.AppointmentRepository;
import com.khan.hospital_management.repository.DoctorRepository;
import com.khan.hospital_management.repository.MedicalRecordRepository;
import com.khan.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordDto createRecord(MedicalRecordRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor Not Found with id: " + request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient Not Found with id: " + request.getPatientId()));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment Not Found with id: " + request.getAppointmentId()));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new InvalidAppointmentException("Appointment doctor does not match with the doctor id provided");
        }

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new InvalidAppointmentException("Appointment patient does not match with the patient id provided");
        }

        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED)) {
            throw new InvalidAppointmentException("Medical record can only be created for completed appointments");
        }

        MedicalRecord record = MedicalRecord.builder()
                .diagnosis(request.getDiagnosis())
                .treatment(request.getTreatment())
                .notes(request.getNotes())
                .doctor(doctor)
                .patient(patient)
                .appointment(appointment)
                .build();

        MedicalRecord savedMedicalReport = medicalRecordRepository.save(record);

        return mapToDto(savedMedicalReport);
    }

    public Page<MedicalRecordDto> getMedicalRecordsByPatientId(Long patientId, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.findMedicalRecordsByPatientId(patientId, pageable);
        return medicalRecords.map(this::mapToDto);
    }

    public MedicalRecordDto updateRecord(Long id, MedicalRecordRequest request) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        record.setDiagnosis(request.getDiagnosis());
        record.setTreatment(request.getTreatment());
        record.setNotes(request.getNotes());

        return mapToDto(medicalRecordRepository.save(record));
    }

    public void deleteRecord(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found  with id: " + id));
        medicalRecordRepository.delete(record);
    }

    private MedicalRecordDto mapToDto(MedicalRecord record) {
        return MedicalRecordDto.builder()
                .id(record.getId())
                .diagnosis(record.getDiagnosis())
                .treatment(record.getTreatment())
                .notes(record.getNotes())
                .doctorId(record.getDoctor().getId())
                .patientId(record.getPatient().getId())
                .appointmentId(record.getAppointment().getId())
                .build();
    }
}
