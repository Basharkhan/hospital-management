package com.khan.hospital_management.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
    private String headOfDepartment;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
