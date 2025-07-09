package com.khan.hospital_management.repository;

import com.khan.hospital_management.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
