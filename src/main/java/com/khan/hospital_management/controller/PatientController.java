package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.ApiResponse;
import com.khan.hospital_management.dto.DepartmentDto;
import com.khan.hospital_management.dto.PatientDto;
import com.khan.hospital_management.dto.PatientUpdateRequest;
import com.khan.hospital_management.service.PatientService;
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
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private  final PatientService patientService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PatientDto>> updatePatient(@PathVariable Long id,
                                                                 @Valid @RequestBody PatientUpdateRequest request) {
        PatientDto patient = patientService.updatePatient(id, request);

        ApiResponse<PatientDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient updated successfully",
                patient,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PatientDto>>> getAllActivePatients(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<PatientDto> patients = patientService.getAllActivePatients(pageable);

        ApiResponse<Page<PatientDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Active patients retrieved successfully",
                patients,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PatientDto>>> getAllPatients(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<PatientDto> department = patientService.getAllPatients(pageable);

        ApiResponse<Page<PatientDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patients retrieved successfully",
                department,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PatientDto>> getPatientById(@PathVariable Long id) {
        PatientDto patient = patientService.getPatientById(id);

        ApiResponse<PatientDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient retrieved successfully",
                patient,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivatePatient(@PathVariable Long id) {
        patientService.deactivatePatient(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient deactivated successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activatePatient(@PathVariable Long id) {
        patientService.activatePatient(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient activated successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteDepartmentById(@PathVariable Long id) {
        patientService.deletePatientById(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
