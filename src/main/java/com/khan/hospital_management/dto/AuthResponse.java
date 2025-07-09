package com.khan.hospital_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String message;
    private LocalDateTime timestamp;
    private UserDetailsDto userDetailsDto;
}
