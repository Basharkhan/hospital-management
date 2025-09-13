package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        var department = departmentService.createDepartment(request);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department created successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@PathVariable Long id,
                                                                       @Valid @RequestBody DepartmentRequest request) {
        var department = departmentService.updateDepartment(id, request);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department updated successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<DepartmentDto>>> getDoctorsByPage(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<DepartmentDto> department = departmentService.getDepartments(pageable);

        ApiResponse<Page<DepartmentDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartment(@PathVariable Long id) {
        var department = departmentService.getDepartment(id);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
