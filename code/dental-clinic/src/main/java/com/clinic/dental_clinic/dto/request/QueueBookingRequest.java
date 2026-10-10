package com.clinic.dental_clinic.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QueueBookingRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Dentist ID is required")
    private Long dentistId;

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @NotNull(message = "Base price is required")
    @Min(value = 0, message = "Base price cannot be negative")
    private Double basePrice;

    public QueueBookingRequest() {
    }

    public QueueBookingRequest(Long patientId, Long dentistId, String serviceType, Double basePrice) {
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.serviceType = serviceType;
        this.basePrice = basePrice;
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
}