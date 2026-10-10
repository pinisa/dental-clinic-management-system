package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.Appointment;
import com.clinic.dental_clinic.domain.enums.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDentistIdAndAppointmentDateTimeAndStatusNot(Long dentistId, LocalDateTime appointmentDateTime, QueueStatus status);
    boolean existsByDentistIdAndAppointmentDateTimeAndIdNotAndStatusNot(Long dentistId, LocalDateTime appointmentDateTime, Long id, QueueStatus status);
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
}
