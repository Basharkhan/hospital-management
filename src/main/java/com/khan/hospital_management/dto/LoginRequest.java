package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.Role;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
