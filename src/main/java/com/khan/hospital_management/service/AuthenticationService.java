package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.exception.InvalidCredentialsException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.exception.UserAlreadyExistsException;
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
    public AuthResponse registerAdmin(RegisterRequest request) {
        return registerUser(request, Role.ADMIN);
    }

    @Transactional
    public AuthResponse registerDoctor(DoctorRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
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
                // .department(request.getDepartment())
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
                .userDetailsDto(userDetailsDto)
                .build();
    }

    @Transactional
    public AuthResponse registerEmployee(RegisterRequest request) {
        return registerUser(request, Role.EMPLOYEE);
    }

    @Transactional
    public AuthResponse registerPatient(RegisterRequest request) {
        return registerUser(request, Role.PATIENT);
    }

    public AuthResponse registerUser(RegisterRequest request, Role role) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        // create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        // save user
        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .build();

        return AuthResponse.builder()
                .token(token)
                .userDetailsDto(userDetailsDto)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();

        return AuthResponse.builder()
                .token(token)
                .userDetailsDto(userDetailsDto)
                .build();
    }
}
