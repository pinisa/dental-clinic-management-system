package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Long> {

    Optional<Dentist> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);
}