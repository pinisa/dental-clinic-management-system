package com.clinic.dental_clinic.repository;

import com.clinic.dental_clinic.domain.entity.TreatmentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, Long> {

    Page<TreatmentRecord> findByPatientId(Long patientId, Pageable pageable);

    List<TreatmentRecord> findByDentistId(Long dentistId);
}