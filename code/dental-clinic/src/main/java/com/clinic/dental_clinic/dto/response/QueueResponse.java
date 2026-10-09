package com.clinic.dental_clinic.dto.response;

import com.clinic.dental_clinic.domain.enums.QueueStatus;

import java.time.LocalDateTime;

public class QueueResponse {

    private Long id;
    private String queueNumber;
    private String serviceType;
    private Double basePrice;
    private Double finalPrice;
    private QueueStatus status;
    private LocalDateTime createdAt;
    private Long patientId;
    private String patientName;
    private Long dentistId;
    private String dentistName;

    public QueueResponse() {
    }

    public QueueResponse(Long id, String queueNumber, String serviceType, Double basePrice, Double finalPrice, QueueStatus status, LocalDateTime createdAt, Long patientId, String patientName, Long dentistId, String dentistName) {
        this.id = id;
        this.queueNumber = queueNumber;
        this.serviceType = serviceType;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.patientId = patientId;
        this.patientName = patientName;
        this.dentistId = dentistId;
        this.dentistName = dentistName;
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
}