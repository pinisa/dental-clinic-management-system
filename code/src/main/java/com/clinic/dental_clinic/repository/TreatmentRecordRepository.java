package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.model.TreatmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, Long> {
}