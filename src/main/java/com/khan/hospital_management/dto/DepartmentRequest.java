package com.khan.hospital_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentRequest {
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description can not exceed 500 characters")
    private String description;

    @NotBlank(message = "Head of department is required")
    @Size(min = 2, max = 100, message = "Head of department must be between 2 and 100 characters")
    private String headOfDepartment;
}
