package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final Environment env;

    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<AuthResponse>> registerAdmin(@RequestHeader("X-SETUP-KEY") String setupKey,
                                                                   @Valid @RequestBody RegisterRequest request) {
        String expectedKey = env.getProperty("app.setup.key");

        if (expectedKey == null || !expectedKey.equals(setupKey)) {
            throw new AccessDeniedException("Access denied");
        }

        AuthResponse authResponse = authenticationService.registerAdmin(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Admin registered successfully",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/doctor")
    public ResponseEntity<ApiResponse<AuthResponse>> registerDoctor(@Valid @RequestBody DoctorRegisterRequest registerRequest) {
        AuthResponse authResponse = authenticationService.registerDoctor(registerRequest);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor registered successfully",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/employee")
    public ResponseEntity<ApiResponse<AuthResponse>> registerEmployee(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authenticationService.registerEmployee(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Employee registered successfully",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/register/patient")
    public ResponseEntity<ApiResponse<AuthResponse>> registerPatient(@Valid @RequestBody PatientRegisterRequest request) {
        AuthResponse authResponse = authenticationService.registerPatient(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Patient registered successfully",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authenticationService.login(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Login successful",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }
}
