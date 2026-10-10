package com.clinic.dental_clinic.service.impl;

import com.clinic.dental_clinic.domain.entity.Appointment;
import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.dto.mapper.AppointmentMapper;
import com.clinic.dental_clinic.dto.request.AppointmentRequest;
import com.clinic.dental_clinic.dto.response.AppointmentResponse;
import com.clinic.dental_clinic.exception.ResourceConflictException;
import com.clinic.dental_clinic.exception.ResourceNotFoundException;
import com.clinic.dental_clinic.repository.AppointmentRepository;
import com.clinic.dental_clinic.repository.DentistRepository;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
                                  DentistRepository dentistRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
    }

    @Override @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        if (appointmentRepository.existsByDentistIdAndAppointmentDateTimeAndStatusNot(request.getDentistId(), request.getAppointmentDateTime(), com.clinic.dental_clinic.domain.enums.QueueStatus.CANCELLED)) {
            throw new ResourceConflictException("Dentist already has an appointment at this time");
        }
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));
        Dentist dentist = dentistRepository.findById(request.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + request.getDentistId()));
        Appointment a = new Appointment();
        a.setPatient(patient); a.setDentist(dentist);
        a.setAppointmentDateTime(request.getAppointmentDateTime());
        a.setServiceType(request.getServiceType());
        return AppointmentMapper.toResponse(appointmentRepository.save(a));
    }

    @Override @Transactional(readOnly = true)
    public AppointmentResponse getById(Long id) {
        return AppointmentMapper.toResponse(appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id)));
    }

    @Override @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(AppointmentMapper::toResponse);
    }

    @Override @Transactional(readOnly = true)
    public Page<AppointmentResponse> getByPatientId(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return appointmentRepository.findByPatientId(patientId, pageable).map(AppointmentMapper::toResponse);
    }

    @Override @Transactional
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        if (appointmentRepository.existsByDentistIdAndAppointmentDateTimeAndIdNotAndStatusNot(request.getDentistId(), request.getAppointmentDateTime(), id, com.clinic.dental_clinic.domain.enums.QueueStatus.CANCELLED)) {
            throw new ResourceConflictException("Dentist already has an appointment at this time");
        }
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));
        Dentist dentist = dentistRepository.findById(request.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + request.getDentistId()));
        a.setPatient(patient); a.setDentist(dentist);
        a.setAppointmentDateTime(request.getAppointmentDateTime());
        a.setServiceType(request.getServiceType());
        return AppointmentMapper.toResponse(appointmentRepository.save(a));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
