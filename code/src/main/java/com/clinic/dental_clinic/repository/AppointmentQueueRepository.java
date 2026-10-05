package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.model.AppointmentQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentQueueRepository extends JpaRepository<AppointmentQueue, Long> {
}