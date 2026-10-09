package com.clinic.dental_clinic.dto.request;

import com.clinic.dental_clinic.domain.enums.PatientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientRequest {

    @NotBlank(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "Phone number must be 9-10 digits")
    private String phone;

    @NotNull(message = "Coverage type is required")
    private PatientType coverageType;

    private String medicalHistory;
    private String allergies;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;

    public PatientRequest() {
    }

    public PatientRequest(String name, String phone, PatientType coverageType, String medicalHistory, String allergies, String emergencyContact) {
        this.name = name;
        this.phone = phone;
        this.coverageType = coverageType;
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
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