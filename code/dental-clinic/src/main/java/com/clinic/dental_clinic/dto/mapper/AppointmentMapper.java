package com.clinic.dental_clinic.dto.mapper;

import com.clinic.dental_clinic.domain.entity.Appointment;
import com.clinic.dental_clinic.dto.response.AppointmentResponse;

public final class AppointmentMapper {
    private AppointmentMapper() {}
    public static AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(a.getId(), a.getAppointmentDateTime(), a.getServiceType(), a.getStatus(),
                a.getPatient().getId(), a.getPatient().getName(), a.getDentist().getId(), a.getDentist().getName());
    }
}
