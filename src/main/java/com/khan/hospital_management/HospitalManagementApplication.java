package com.khan.hospital_management;

import com.khan.hospital_management.model.*;
import com.khan.hospital_management.repository.DepartmentRepository;
import com.khan.hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@RequiredArgsConstructor
public class HospitalManagementApplication {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}

    // @Bean
    public CommandLineRunner runner() {
        return args -> {
            // ======== Departments ========
            Department medicine = Department.builder()
                    .name("Medicine")
                    .description("Medicine description")
                    .active(true)
                    .headOfDepartment("No One")
                    .build();

            Department cardiology = Department.builder()
                    .name("Cardiology")
                    .description("Cardiology description")
                    .active(true)
                    .headOfDepartment("No One")
                    .build();

            Department dental = Department.builder()
                    .name("Dental")
                    .description("Dental description")
                    .active(true)
                    .headOfDepartment("No One")
                    .build();
            departmentRepository.saveAll(List.of(medicine, cardiology, dental));

            // ======== Admin ========
            User admin = User.builder()
                    .fullName("Admin User")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);

            // ======== Doctors ========
            List<Doctor> doctors = new ArrayList<>();
            int roomCounter = 101;
            for (Department dep : List.of(medicine, cardiology, dental)) {
                for (int i = 1; i <= 3; i++) {
                    User doctorUser = User.builder()
                            .fullName("Dr. " + dep.getName() + " " + i)
                            .email("doctor" + i + "@" + dep.getName().toLowerCase() + ".com")
                            .password(passwordEncoder.encode("12345"))
                            .role(Role.DOCTOR)
                            .active(true)
                            .build();

                    Doctor doctor = Doctor.builder()
                            .user(doctorUser)
                            .specialization(dep.getName())
                            .phone("12345678" + i)
                            .roomNumber(String.valueOf(roomCounter++))
                            .department(dep)
                            .active(true)
                            .build();

                    doctorUser.setDoctor(doctor);
                    userRepository.save(doctorUser);
                    doctors.add(doctor);
                }
            }

            // ======== Patients ========
            for (int i = 1; i <= 5; i++) {
                User patientUser = User.builder()
                        .fullName("Patient " + i)
                        .email("patient" + i + "@example.com")
                        .password(passwordEncoder.encode("12345"))
                        .role(Role.PATIENT)
                        .active(true)
                        .build();

                Patient patient = Patient.builder()
                        .user(patientUser)
                        .phone("987654321" + i)
                        .address("Street " + i + ", City")
                        .dateOfBirth(LocalDate.of(1990 + i, i, i))
                        .build();

                patientUser.setPatient(patient);
                userRepository.save(patientUser);
            }
        };
    }
}
