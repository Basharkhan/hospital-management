package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.MedicalRecordService;
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
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MedicalRecordDto>> createRecord(@Valid @RequestBody MedicalRecordRequest request) {
        MedicalRecordDto record = medicalRecordService.createRecord(request);

        ApiResponse<MedicalRecordDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Record created successfully",
                record,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MedicalRecordDto>> updateRecord(@PathVariable Long id,
                                                                      @Valid @RequestBody MedicalRecordUpdateRequest request) {
        MedicalRecordDto record = medicalRecordService.updateRecord(id, request);

        ApiResponse<MedicalRecordDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Record updated successfully",
                record,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
