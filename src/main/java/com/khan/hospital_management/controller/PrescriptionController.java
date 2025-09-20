package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.PrescriptionService;
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
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PrescriptionDto>> createPrescription(@Valid @RequestBody PrescriptionRequest request) {
        PrescriptionDto prescription = prescriptionService.createPrescription(request);

        ApiResponse<PrescriptionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Prescription created successfully",
                prescription,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PrescriptionDto>> getPrescriptionById(@PathVariable Long id) {
        PrescriptionDto prescription = prescriptionService.getPrescriptionById(id);

        ApiResponse<PrescriptionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Prescription retrieved successfully",
                prescription,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PrescriptionDto>>> getPrescriptionsByPatientId(
            @PathVariable Long patientId,
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId, pageable);

        ApiResponse<Page<PrescriptionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Prescriptions by patient retrieved successfully",
                prescriptions,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PrescriptionDto>>> getPrescriptionsByDoctorId(
            @PathVariable Long doctorId,
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByDoctorId(doctorId, pageable);

        ApiResponse<Page<PrescriptionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Prescriptions by doctor retrieved successfully",
                prescriptions,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
