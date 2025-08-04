package com.khan.hospital_management.controller;

import com.khan.hospital_management.dto.AuthResponse;
import com.khan.hospital_management.dto.DoctorRegisterRequest;
import com.khan.hospital_management.dto.LoginRequest;
import com.khan.hospital_management.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody DoctorRegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.registerAdmin(registerRequest));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<AuthResponse> registerDoctor(@Valid @RequestBody DoctorRegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.registerDoctor(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
