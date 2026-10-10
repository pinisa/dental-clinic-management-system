package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.dto.request.AppointmentRequest;
import com.clinic.dental_clinic.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentResponse create(AppointmentRequest request);
    AppointmentResponse getById(Long id);
    Page<AppointmentResponse> getAll(Pageable pageable);
    Page<AppointmentResponse> getByPatientId(Long patientId, Pageable pageable);
    AppointmentResponse update(Long id, AppointmentRequest request);
    void delete(Long id);
}
