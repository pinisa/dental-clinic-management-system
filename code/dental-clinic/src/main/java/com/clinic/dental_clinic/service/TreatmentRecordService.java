package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.dto.request.TreatmentRecordRequest;
import com.clinic.dental_clinic.dto.response.TreatmentRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreatmentRecordService {
    TreatmentRecordResponse createTreatmentRecord(TreatmentRecordRequest request);
    TreatmentRecordResponse getTreatmentRecordById(Long id);
    Page<TreatmentRecordResponse> getTreatmentRecordsByPatientId(Long patientId, Pageable pageable);
}