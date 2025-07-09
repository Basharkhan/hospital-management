package com.khan.hospital_management.dto.response;

import com.khan.hospital_management.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {
    private String email;
    private String fullName;
    private Role role;
}
