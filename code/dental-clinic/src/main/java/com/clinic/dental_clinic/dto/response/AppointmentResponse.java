package com.clinic.dental_clinic.dto.response;

import com.clinic.dental_clinic.domain.enums.QueueStatus;
import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDateTime;
    private String serviceType;
    private QueueStatus status;
    private Long patientId;
    private String patientName;
    private Long dentistId;
    private String dentistName;

    public AppointmentResponse() {}
    public AppointmentResponse(Long id, LocalDateTime appointmentDateTime, String serviceType, QueueStatus status,
                               Long patientId, String patientName, Long dentistId, String dentistName) {
        this.id = id; this.appointmentDateTime = appointmentDateTime; this.serviceType = serviceType; this.status = status;
        this.patientId = patientId; this.patientName = patientName; this.dentistId = dentistId; this.dentistName = dentistName;
    }
    public Long getId() { return id; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public String getServiceType() { return serviceType; }
    public QueueStatus getStatus() { return status; }
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public Long getDentistId() { return dentistId; }
    public String getDentistName() { return dentistName; }
}
