package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
}