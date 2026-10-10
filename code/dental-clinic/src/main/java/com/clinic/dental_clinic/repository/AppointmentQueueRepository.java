package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.AppointmentQueue;
import com.clinic.dental_clinic.domain.enums.QueueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentQueueRepository extends JpaRepository<AppointmentQueue, Long> {

    Page<AppointmentQueue> findByPatientId(Long patientId, Pageable pageable);

    List<AppointmentQueue> findByStatus(QueueStatus status);

    List<AppointmentQueue> findByDentistIdAndStatus(Long dentistId, QueueStatus status);

    boolean existsByDentistIdAndStatus(Long dentistId, QueueStatus status);
}