package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DepartmentRequest;
import com.khan.hospital_management.dto.DepartmentDto;
import com.khan.hospital_management.exception.ResourceAlreadyExistsException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Department;
import com.khan.hospital_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentDto createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByName(departmentRequest.getName())) {
            throw new ResourceAlreadyExistsException("Department already exists");
        }

        Department department = Department.builder()
                .name(departmentRequest.getName())
                .description(departmentRequest.getDescription())
                .headOfDepartment(departmentRequest.getHeadOfDepartment())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
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
        department.setUpdatedAt(LocalDateTime.now());

        Department savedDepartment = departmentRepository.save(department);

        return mapToDto(savedDepartment);
    }

    public void deactivateDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        department.setActive(false);
        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public DepartmentDto getDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        return mapToDto(department);
    }

    @Transactional(readOnly = true)
    public Page<DepartmentDto> getDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(this::mapToDto);
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
