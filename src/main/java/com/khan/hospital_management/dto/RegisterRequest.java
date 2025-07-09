package com.khan.hospital_management.dto;

import com.khan.hospital_management.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}
