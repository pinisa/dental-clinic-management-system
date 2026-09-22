package com.clinic.dental_clinic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AppointmentQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queueNumber;
    private String serviceType;
    private Double basePrice;
    private Double finalPrice;

    @Enumerated(EnumType.STRING)
    private QueueStatus status = QueueStatus.WAITING;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    private Dentist dentist;

    public AppointmentQueue() {
    }

    public AppointmentQueue(Long id, String queueNumber, String serviceType, Double basePrice, Double finalPrice, QueueStatus status, LocalDateTime createdAt, Patient patient, Dentist dentist) {
        this.id = id;
        this.queueNumber = queueNumber;
        this.serviceType = serviceType;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.patient = patient;
        this.dentist = dentist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}