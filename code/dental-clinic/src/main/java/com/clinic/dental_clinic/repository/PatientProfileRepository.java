package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
}
