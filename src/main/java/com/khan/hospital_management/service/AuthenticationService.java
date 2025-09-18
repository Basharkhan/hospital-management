package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.*;
import com.khan.hospital_management.exception.InvalidCredentialsException;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.exception.UserAlreadyExistsException;
import com.khan.hospital_management.model.*;
import com.khan.hospital_management.repository.DepartmentRepository;
import com.khan.hospital_management.repository.UserRepository;
import com.khan.hospital_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DepartmentRepository departmentRepository;

    public AuthResponse registerAdmin(RegisterRequest request) {
        return registerUser(request, Role.ADMIN);
    }

    public AuthResponse registerEmployee(RegisterRequest request) {
        return registerUser(request, Role.EMPLOYEE);
    }

    public AuthResponse registerDoctor(DoctorRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        // create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DOCTOR)
                .active(true)
                .build();

        // create doctor profile
        Doctor doctor = Doctor.builder()
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .department(department)
                .user(user)
                .roomNumber(request.getRoomNumber())
                .active(true)
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

    public AuthResponse registerPatient(PatientRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .active(true)
                .build();

        Patient patient = Patient.builder()
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .user(user)
                .build();

        // link patient and user
        user.setPatient(patient);
        User savedUser = userRepository.save(user);

        // Generate JWT
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
                .active(true)
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
