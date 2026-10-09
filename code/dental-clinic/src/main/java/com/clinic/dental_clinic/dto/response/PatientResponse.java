package com.clinic.dental_clinic.dto.response;

import com.clinic.dental_clinic.domain.enums.PatientType;

public class PatientResponse {

    private Long id;
    private String name;
    private String phone;
    private PatientType coverageType;
    private String medicalHistory;
    private String allergies;
    private String emergencyContact;

    public PatientResponse() {
    }

    public PatientResponse(Long id, String name, String phone, PatientType coverageType, String medicalHistory, String allergies, String emergencyContact) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.coverageType = coverageType;
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PatientType getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(PatientType coverageType) {
        this.coverageType = coverageType;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}