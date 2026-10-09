package com.clinic.dental_clinic.dto.mapper;

import com.clinic.dental_clinic.domain.entity.TreatmentRecord;
import com.clinic.dental_clinic.dto.response.TreatmentRecordResponse;

public class TreatmentRecordMapper {

    public static TreatmentRecordResponse toResponse(TreatmentRecord record) {
        return new TreatmentRecordResponse(
                record.getId(),
                record.getPatient() != null ? record.getPatient().getId() : null,
                record.getPatient() != null ? record.getPatient().getName() : null,
                record.getDentist() != null ? record.getDentist().getId() : null,
                record.getDentist() != null ? record.getDentist().getName() : null,
                record.getAppointmentQueue() != null ? record.getAppointmentQueue().getId() : null,
                record.getDiagnosisNote(),
                record.getTotalCost(),
                record.getRecordDate()
        );
    }
}