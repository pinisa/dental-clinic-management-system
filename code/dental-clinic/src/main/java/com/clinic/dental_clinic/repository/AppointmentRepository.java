package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDentistIdAndAppointmentDateTime(Long dentistId, LocalDateTime appointmentDateTime);
    boolean existsByDentistIdAndAppointmentDateTimeAndIdNot(Long dentistId, LocalDateTime appointmentDateTime, Long id);
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
}
