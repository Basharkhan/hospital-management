package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DepartmentRequest;
import com.khan.hospital_management.dto.DepartmentDto;
import com.khan.hospital_management.exception.ResourceAlreadyExistsException;
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
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentDto createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByName(departmentRequest.getName())) {
            throw new ResourceAlreadyExistsException("Department already exists");
        }

        Department department = Department.builder()
                .name(departmentRequest.getName())
                .description(departmentRequest.getDescription())
                .headOfDepartment(departmentRequest.getHeadOfDepartment())
                .active(true)
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return mapToDto(savedDepartment);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        department.setName(departmentRequest.getName());
        department.setDescription(departmentRequest.getDescription());
        department.setHeadOfDepartment(departmentRequest.getHeadOfDepartment());

        Department savedDepartment = departmentRepository.save(department);

        return mapToDto(savedDepartment);
    }

    public void activateDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        department.setActive(true);
        departmentRepository.save(department);
    }

    public void deactivateDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        department.setActive(false);
        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        return mapToDto(department);
    }

    @Transactional(readOnly = true)
    public Page<DepartmentDto> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public Page<DepartmentDto> getAllActiveDepartments(Pageable pageable) {
        return departmentRepository.findAllByActiveTrue(pageable).map(this::mapToDto);
    }

    public void deleteDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    public void assignDoctorToDepartment(Long departmentId, Long doctorId) {
        Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        department.getDoctors().add(doctor);
        // System.out.println(department);
        departmentRepository.save(department);
    }

    public void removeDoctorFromDepartment(Long departmentId, Long doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        department.getDoctors().remove(doctor);
        departmentRepository.save(department);
    }

    private DepartmentDto mapToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .headOfDepartment(department.getHeadOfDepartment())
                .active(department.isActive())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}
