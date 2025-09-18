package com.khan.hospital_management.service;

import com.khan.hospital_management.dto.AppointmentDto;
import com.khan.hospital_management.dto.AppointmentRequest;
import com.khan.hospital_management.dto.AppointmentUpdateRequest;
import com.khan.hospital_management.exception.ResourceNotFoundException;
import com.khan.hospital_management.model.Appointment;
import com.khan.hospital_management.model.AppointmentStatus;
import com.khan.hospital_management.model.Doctor;
import com.khan.hospital_management.model.Patient;
import com.khan.hospital_management.repository.AppointmentRepository;
import com.khan.hospital_management.repository.DoctorRepository;
import com.khan.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentDto createAppointment(AppointmentRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));

        Appointment appointment = Appointment.builder()
                .appointmentDate(request.getAppointmentDate())
                .appointmentStatus(AppointmentStatus.PENDING)
                .reason(request.getReason())
                .doctor(doctor)
                .patient(patient)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return mapToDto(appointment);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentsByDoctor(Long doctorId, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAllByDoctorId(doctorId, pageable);
        return appointments.map(this::mapToDto);
    }

    public AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return mapToDto(updatedAppointment);
    }

    public AppointmentDto confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id);

        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return mapToDto(appointment);
    }

    public AppointmentDto cancelAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        return mapToDto(appointmentRepository.save(appointment));
    }

    public AppointmentDto completeAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        return mapToDto(appointmentRepository.save(appointment));
    }

    public Appointment findAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return appointment;
    }

    private AppointmentDto mapToDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentStatus(appointment.getAppointmentStatus())
                .reason(appointment.getReason())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getUser().getFullName())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getUser().getFullName())
                .build();
    }
}
