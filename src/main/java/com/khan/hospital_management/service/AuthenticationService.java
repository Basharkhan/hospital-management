package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.model.Doctor;
import com.khan.hospital_management.model.Role;
import com.khan.hospital_management.model.User;
import com.khan.hospital_management.repository.UserRepository;
import com.khan.hospital_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse registerDoctor(DoctorRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DOCTOR)
                .createdAt(LocalDateTime.now())
                .build();

        // create doctor profile
        Doctor doctor = Doctor.builder()
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .department(request.getDepartment())
                .qualification(request.getQualification())
                .user(user) // <-- THIS IS IMPORTANT
                .build();

        // link doctor to user
        user.setDoctor(doctor);

        // save doctor
        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .build();

        return AuthResponse.builder()
                .token(token)
                .message("Doctor registered successfully!")
                .timestamp(LocalDateTime.now())
                .userDetailsDto(userDetailsDto)
                .build();
    }

//    public AuthResponse register(RegisterRequest registerRequest) {
//        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
//            throw new RuntimeException("User already exists");
//        }
//
//        var user = User.builder()
//                .fullName(registerRequest.getFullName())
//                .email(registerRequest.getEmail())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .role(registerRequest.getRole())
//                .build();
//        userRepository.save(user);
//        String token = jwtService.generateToken(user);
//        return new AuthResponse(token);
//    }
//
//    public AuthResponse login(LoginRequest loginRequest) {
//        User user = userRepository.findByEmail(loginRequest.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//        String token = jwtService.generateToken(user);
//        return new AuthResponse(token);
//    }
}
