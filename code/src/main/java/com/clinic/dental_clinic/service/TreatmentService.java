package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.model.*;
import com.clinic.dental_clinic.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TreatmentService {

    private final TreatmentRecordRepository treatmentRecordRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    public TreatmentService(TreatmentRecordRepository treatmentRecordRepository,
                            PatientRepository patientRepository,
                            DentistRepository dentistRepository) {
        this.treatmentRecordRepository = treatmentRecordRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
    }

    @Transactional
    public TreatmentRecord recordTreatment(Long patientId, Long dentistId, String diagnosisNote, Double totalCost) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Dentist dentist = dentistRepository.findById(dentistId)
                .orElseThrow(() -> new IllegalArgumentException("Dentist not found"));

        TreatmentRecord record = new TreatmentRecord();
        record.setPatient(patient);
        record.setDentist(dentist);
        record.setDiagnosisNote(diagnosisNote);
        record.setTotalCost(totalCost);

        return treatmentRecordRepository.save(record);
    }
}