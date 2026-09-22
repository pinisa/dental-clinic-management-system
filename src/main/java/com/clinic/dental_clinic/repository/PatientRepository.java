package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}