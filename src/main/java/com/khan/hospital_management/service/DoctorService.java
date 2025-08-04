package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DoctorDto;
import com.khan.hospital_management.dto.DoctorUpdateRequest;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Doctor;
import com.khan.hospital_management.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<DoctorDto> getAllDoctors() {
        return doctorRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DoctorDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
    }

    public DoctorDto updateDoctor(Long id, DoctorUpdateRequest  request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhone(request.getPhone());
        doctor.setDepartment(request.getDepartment());
        doctor.setQualification(request.getQualification());

        return toDto(doctorRepository.save(doctor));
    }

    private DoctorDto toDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .fullName(doctor.getUser().getFullName())
                .email(doctor.getUser().getEmail())
                .specialization(doctor.getSpecialization())
                .phone(doctor.getPhone())
                .department(doctor.getDepartment())
                .qualification(doctor.getQualification())
                .build();
    }
}
