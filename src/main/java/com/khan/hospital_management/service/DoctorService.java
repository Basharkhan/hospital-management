package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DoctorDto;
import com.khan.hospital_management.dto.DoctorUpdateRequest;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Doctor;
import com.khan.hospital_management.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {
    private final DoctorRepository doctorRepository;

//    @Transactional(readOnly = true)
//    public Page<DoctorDto> findDoctorsByPage(Pageable pageable) {
//        return doctorRepository.findAllByActiveTrue(pageable)
//                .map(this::toDto);
//    }
//
//    @Transactional(readOnly = true)
//    public DoctorDto findDoctorById(Long id) {
//        return doctorRepository.findById(id)
//                .map(this::toDto)
//                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
//    }

//    public DoctorDto updateDoctor(Long id, DoctorUpdateRequest  request) {
//        Doctor doctor = doctorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
//
//        doctor.setSpecialization(request.getSpecialization());
//        doctor.setPhone(request.getPhone());
//        // doctor.setDepartment(request.getDepartment());
//        doctor.setQualification(request.getQualification());
//
//        return toDto(doctorRepository.save(doctor));
//    }

    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

        doctor.setActive(false);
        doctorRepository.save(doctor);
    }

//    private DoctorDto toDto(Doctor doctor) {
//        return DoctorDto.builder()
//                .id(doctor.getId())
//                .fullName(doctor.getUser().getFullName())
//                .email(doctor.getUser().getEmail())
//                .specialization(doctor.getSpecialization())
//                .phone(doctor.getPhone())
//                // .department(doctor.getDepartment())
//                .qualification(doctor.getQualification())
//                .build();
//    }
}
