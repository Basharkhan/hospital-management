package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.PatientDto;
import com.khan.hospital_management.dto.PatientUpdateRequest;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Patient;
import com.khan.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public Page<PatientDto> getAllActivePatients(Pageable pageable) {
        return patientRepository.findByActiveTrue(pageable)
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public Page<PatientDto> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public PatientDto updatePatient(Long id, PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.getUser().setFullName(request.getFullName());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());
        patient.setDateOfBirth(request.getDateOfBirth());

        patientRepository.save(patient);

        return mapToDto(patient);
    }

    public void deactivatePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setActive(false);
        patientRepository.save(patient);
    }

    public void activatePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setActive(true);
        patientRepository.save(patient);
    }

    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }

    private PatientDto mapToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .phone(patient.getPhone())
                .dateOfBirth(patient.getDateOfBirth())
                .active(patient.isActive())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }
}
