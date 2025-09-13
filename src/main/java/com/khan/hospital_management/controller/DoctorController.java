package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.ApiResponse;
import com.khan.hospital_management.dto.DoctorDto;
import com.khan.hospital_management.dto.DoctorUpdateRequest;
import com.khan.hospital_management.service.DoctorService;
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
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

//    @GetMapping()
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse<Page<DoctorDto>>> getDoctorsByPage(@PageableDefault(size = 10, page = 0) Pageable pageable) {
//        Page<DoctorDto> doctors = doctorService.findDoctorsByPage(pageable);
//
//
//        ApiResponse<Page<DoctorDto>> response = new ApiResponse<>(
//                HttpStatus.OK.value(),
//                "Doctors retrieved successfully",
//                doctors,
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
//    public ResponseEntity<ApiResponse<DoctorDto>> getDoctorById(@PathVariable Long id) {
//        DoctorDto doctor = doctorService.findDoctorById(id);
//
//        ApiResponse<DoctorDto> response = new ApiResponse<>(
//                HttpStatus.OK.value(),
//                "Doctor retrieved successfully",
//                doctor,
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
//    public ResponseEntity<ApiResponse<DoctorDto>> updateDoctor(@PathVariable Long id,
//                                                  @RequestBody DoctorUpdateRequest request) {
//        DoctorDto doctor = doctorService.updateDoctor(id, request);
//
//        ApiResponse<DoctorDto> response = new ApiResponse<>(
//                HttpStatus.OK.value(),
//                "Doctor updated successfully",
//                doctor,
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
