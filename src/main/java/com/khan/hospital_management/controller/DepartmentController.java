package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        var department = departmentService.createDepartment(request);

        ApiResponse<DepartmentResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department created successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
