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
    public ResponseEntity<ApiResponse<Page<DepartmentDto>>> getAllDepartments(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<DepartmentDto> department = departmentService.getAllDepartments(pageable);

        ApiResponse<Page<DepartmentDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Departments retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<DepartmentDto>>> getAllActiveDepartments(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<DepartmentDto> department = departmentService.getAllActiveDepartments(pageable);

        ApiResponse<Page<DepartmentDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Active departments retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(@PathVariable Long id) {
        var department = departmentService.getDepartmentById(id);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{departmentId}/doctors/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> assignDoctorToDepartment(@PathVariable Long departmentId,
                                                                      @PathVariable Long doctorId) {
        departmentService.assignDoctorToDepartment(departmentId, doctorId);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor assigned to department successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{departmentId}/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removeDoctorFromDepartment(@PathVariable Long departmentId,
                                                                        @PathVariable Long doctorId) {
        departmentService.removeDoctorFromDepartment(departmentId, doctorId);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor removed from department successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteDepartmentById(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateDepartment(@PathVariable Long id) {
        departmentService.deactivateDepartment(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department deactivated successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateDepartment(@PathVariable Long id) {
        departmentService.activateDepartment(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department activated successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
