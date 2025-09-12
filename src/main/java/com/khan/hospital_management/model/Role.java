package com.khan.hospital_management.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    DOCTOR,
    PATIENT,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
