package com.khan.hospital_management.dto;

import com.khan.hospital_management.dto.response.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private LocalDateTime timestamp;
    private UserDetailsDto userDetailsDto;
}
