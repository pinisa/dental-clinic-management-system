package com.clinic.dental_clinic.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_records")
public class TreatmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diagnosis_note", nullable = false, columnDefinition = "TEXT")
    private String diagnosisNote;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "record_date", nullable = false, updatable = false)
    private LocalDateTime recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_queue_id", nullable = false, unique = true)
    private AppointmentQueue appointmentQueue;

    public TreatmentRecord() {
    }

    public TreatmentRecord(Long id, String diagnosisNote, Double totalCost, LocalDateTime recordDate, Patient patient, Dentist dentist, AppointmentQueue appointmentQueue) {
        this.id = id;
        this.diagnosisNote = diagnosisNote;
        this.totalCost = totalCost;
        this.recordDate = recordDate;
        this.patient = patient;
        this.dentist = dentist;
        this.appointmentQueue = appointmentQueue;
    }

    @PrePersist
    protected void onCreate() {
        this.recordDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public AppointmentQueue getAppointmentQueue() {
        return appointmentQueue;
    }

    public void setAppointmentQueue(AppointmentQueue appointmentQueue) {
        this.appointmentQueue = appointmentQueue;
    }
}