package com.clinic.dental_clinic.dto.mapper;

import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.entity.PatientProfile;
import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.response.PatientResponse;

public class PatientMapper {

    public static Patient toEntity(PatientRequest request) {
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setPhone(request.getPhone());
        patient.setCoverageType(request.getCoverageType());

        PatientProfile profile = new PatientProfile();
        profile.setMedicalHistory(request.getMedicalHistory());
        profile.setAllergies(request.getAllergies());
        profile.setEmergencyContact(request.getEmergencyContact());
        profile.setPatient(patient);

        patient.setProfile(profile);
        return patient;
    }

    public static PatientResponse toResponse(Patient patient) {
        PatientProfile profile = patient.getProfile();
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getPhone(),
                patient.getCoverageType(),
                profile != null ? profile.getMedicalHistory() : null,
                profile != null ? profile.getAllergies() : null,
                profile != null ? profile.getEmergencyContact() : null
        );
    }
}