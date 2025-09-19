package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.ApiResponse;
import com.khan.hospital_management.dto.MedicalRecordDto;
import com.khan.hospital_management.dto.MedicalRecordRequest;
import com.khan.hospital_management.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
