package com.clinic.dental_clinic.service.impl;

import com.clinic.dental_clinic.domain.entity.AppointmentQueue;
import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.entity.TreatmentRecord;
import com.clinic.dental_clinic.domain.enums.QueueStatus;
import com.clinic.dental_clinic.dto.mapper.TreatmentRecordMapper;
import com.clinic.dental_clinic.dto.request.TreatmentRecordRequest;
import com.clinic.dental_clinic.dto.response.TreatmentRecordResponse;
import com.clinic.dental_clinic.exception.ResourceNotFoundException;
import com.clinic.dental_clinic.repository.AppointmentQueueRepository;
import com.clinic.dental_clinic.repository.DentistRepository;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.repository.TreatmentRecordRepository;
import com.clinic.dental_clinic.service.TreatmentRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TreatmentRecordServiceImpl implements TreatmentRecordService {

    private final TreatmentRecordRepository treatmentRecordRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final AppointmentQueueRepository appointmentQueueRepository;

    // Constructor Injection
    public TreatmentRecordServiceImpl(TreatmentRecordRepository treatmentRecordRepository,
                                       PatientRepository patientRepository,
                                       DentistRepository dentistRepository,
                                       AppointmentQueueRepository appointmentQueueRepository) {
        this.treatmentRecordRepository = treatmentRecordRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.appointmentQueueRepository = appointmentQueueRepository;
    }

    @Override
    @Transactional
    public TreatmentRecordResponse createTreatmentRecord(TreatmentRecordRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));

        Dentist dentist = dentistRepository.findById(request.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + request.getDentistId()));

        AppointmentQueue queue = appointmentQueueRepository.findById(request.getAppointmentQueueId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment queue not found with id: " + request.getAppointmentQueueId()));

        // เปลี่ยนสถานะของคิวนัดหมายเป็น COMPLETED เมื่อทำการบันทึกการรักษา
        queue.setStatus(QueueStatus.COMPLETED);
        appointmentQueueRepository.save(queue);

        TreatmentRecord record = new TreatmentRecord();
        record.setPatient(patient);
        record.setDentist(dentist);
        record.setAppointmentQueue(queue);
        record.setDiagnosisNote(request.getDiagnosisNote());
        record.setTotalCost(request.getTotalCost());
        record.setRecordDate(LocalDateTime.now());

        TreatmentRecord savedRecord = treatmentRecordRepository.save(record);
        return TreatmentRecordMapper.toResponse(savedRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentRecordResponse getTreatmentRecordById(Long id) {
        TreatmentRecord record = treatmentRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment record not found with id: " + id));
        return TreatmentRecordMapper.toResponse(record);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentRecordResponse> getTreatmentRecordsByPatientId(Long patientId, Pageable pageable) {
        return treatmentRecordRepository.findByPatientId(patientId, pageable)
                .map(TreatmentRecordMapper::toResponse);
    }
}