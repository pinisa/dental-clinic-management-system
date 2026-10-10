package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.response.PatientResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PatientResponse createPatient(PatientRequest request);
    PatientResponse getPatientById(Long id);
    Optional<PatientResponse> findByPhone(String phone);
    Page<PatientResponse> getAllPatients(Pageable pageable);
    PatientResponse updatePatient(Long id, PatientRequest request);
    void deletePatient(Long id);
}