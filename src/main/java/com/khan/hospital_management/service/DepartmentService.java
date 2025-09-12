package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.DepartmentRequest;
import com.khan.hospital_management.dto.DepartmentResponse;
import com.khan.hospital_management.exception.ResourceAlreadyExistsException;
import com.khan.hospital_management.model.Department;
import com.khan.hospital_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByName(departmentRequest.getName())) {
            throw new ResourceAlreadyExistsException("Department already exists");
        }

        Department department = Department.builder()
                .name(departmentRequest.getName())
                .description(departmentRequest.getDescription())
                .headOfDepartment(departmentRequest.getHeadOfDepartment())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentResponse.builder()
                .id(savedDepartment.getId())
                .name(savedDepartment.getName())
                .description(savedDepartment.getDescription())
                .headOfDepartment(savedDepartment.getHeadOfDepartment())
                .active(savedDepartment.isActive())
                .createdAt(savedDepartment.getCreatedAt())
                .build();
    }
}
