package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.DoctorDto;
import com.khan.hospital_management.dto.DoctorUpdateRequest;
import com.khan.hospital_management.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id,
                                                  @RequestBody DoctorUpdateRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }
}
