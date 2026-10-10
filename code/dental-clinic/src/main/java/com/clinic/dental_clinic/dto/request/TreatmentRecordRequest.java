package com.clinic.dental_clinic.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TreatmentRecordRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Dentist ID is required")
    private Long dentistId;

    @NotNull(message = "Appointment queue ID is required")
    private Long appointmentQueueId;

    @NotBlank(message = "Diagnosis note is required")
    private String diagnosisNote;

    @NotNull(message = "Total cost is required")
    @Min(value = 0, message = "Total cost cannot be negative")
    private Double totalCost;

    public TreatmentRecordRequest() {
    }

    public TreatmentRecordRequest(Long patientId, Long dentistId, Long appointmentQueueId, String diagnosisNote, Double totalCost) {
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.appointmentQueueId = appointmentQueueId;
        this.diagnosisNote = diagnosisNote;
        this.totalCost = totalCost;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDentistId() {
        return dentistId;
    }

    public void setDentistId(Long dentistId) {
        this.dentistId = dentistId;
    }

    public Long getAppointmentQueueId() {
        return appointmentQueueId;
    }

    public void setAppointmentQueueId(Long appointmentQueueId) {
        this.appointmentQueueId = appointmentQueueId;
    }

    public String getDiagnosisNote() {
        return diagnosisNote;
    }

    public void setDiagnosisNote(String diagnosisNote) {
        this.diagnosisNote = diagnosisNote;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}