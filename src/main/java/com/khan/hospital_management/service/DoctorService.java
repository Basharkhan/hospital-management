package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DoctorDto;
import com.khan.hospital_management.dto.DoctorUpdateRequest;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Department;
import com.khan.hospital_management.model.Doctor;
import com.khan.hospital_management.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public Page<DoctorDto> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAllByActiveTrue(pageable)
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public DoctorDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
    }

    public DoctorDto updateDoctor(Long id, DoctorUpdateRequest  request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + request.getDepartmentId() + " not found"));

        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhone(request.getPhone());
        doctor.setDepartment(department);
        doctor.setRoomNumber(request.getRoomNumber());
        doctor.getUser().setFullName(request.getFullName());

        return mapToDto(doctorRepository.save(doctor));
    }

    public void deactivateDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

        doctor.setActive(false);
        doctorRepository.save(doctor);
    }

    public void activateDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

        doctor.setActive(true);
        doctorRepository.save(doctor);
    }

    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
        doctorRepository.delete(doctor);
    }

    private DoctorDto mapToDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .fullName(doctor.getUser().getFullName())
                .email(doctor.getUser().getEmail())
                .specialization(doctor.getSpecialization())
                .phone(doctor.getPhone())
                .department(doctor.getDepartment().getName())
                .build();
    }
}
