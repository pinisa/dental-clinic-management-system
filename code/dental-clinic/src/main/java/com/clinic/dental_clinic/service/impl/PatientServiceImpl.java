package com.clinic.dental_clinic.service.impl;

import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.entity.PatientProfile;
import com.clinic.dental_clinic.dto.mapper.PatientMapper;
import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.response.PatientResponse;
import com.clinic.dental_clinic.exception.ResourceNotFoundException;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Patient with phone number " + request.getPhone() + " already exists.");
        }
        Patient patient = PatientMapper.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return PatientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientResponse> findByPhone(String phone) {
        return patientRepository.findByPhone(phone).map(PatientMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(PatientMapper::toResponse);
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setName(request.getName());
        patient.setPhone(request.getPhone());
        patient.setCoverageType(request.getCoverageType());

        PatientProfile profile = patient.getProfile();
        if (profile == null) {
            profile = new PatientProfile();
            profile.setPatient(patient);
            patient.setProfile(profile);
        }
        profile.setMedicalHistory(request.getMedicalHistory());
        profile.setAllergies(request.getAllergies());
        profile.setEmergencyContact(request.getEmergencyContact());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toResponse(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}