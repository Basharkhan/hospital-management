package com.khan.hospital_management.repository;

import com.khan.hospital_management.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByDoctorId(Long id, Pageable pageable);
    Long countByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
}
