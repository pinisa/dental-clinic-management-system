package com.clinic.dental_clinic.dto.response;

import java.time.LocalDateTime;

public class TreatmentRecordResponse {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long dentistId;
    private String dentistName;
    private Long appointmentQueueId;
    private String diagnosisNote;
    private Double totalCost;
    private LocalDateTime recordDate;

    public TreatmentRecordResponse() {
    }

    public TreatmentRecordResponse(Long id, Long patientId, String patientName, Long dentistId, String dentistName, Long appointmentQueueId, String diagnosisNote, Double totalCost, LocalDateTime recordDate) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.dentistId = dentistId;
        this.dentistName = dentistName;
        this.appointmentQueueId = appointmentQueueId;
        this.diagnosisNote = diagnosisNote;
        this.totalCost = totalCost;
        this.recordDate = recordDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getDentistId() {
        return dentistId;
    }

    public void setDentistId(Long dentistId) {
        this.dentistId = dentistId;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
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

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
}