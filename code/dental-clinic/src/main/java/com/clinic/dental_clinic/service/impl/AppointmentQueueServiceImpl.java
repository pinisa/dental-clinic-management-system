package com.clinic.dental_clinic.service.impl;

import com.clinic.dental_clinic.domain.entity.AppointmentQueue;
import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.enums.QueueStatus;
import com.clinic.dental_clinic.dto.mapper.AppointmentQueueMapper;
import com.clinic.dental_clinic.dto.request.QueueBookingRequest;
import com.clinic.dental_clinic.dto.response.QueueResponse;
import com.clinic.dental_clinic.exception.ResourceNotFoundException;
import com.clinic.dental_clinic.pattern.strategy.PricingContext;
import com.clinic.dental_clinic.pattern.observer.QueueSubject;
import com.clinic.dental_clinic.repository.AppointmentQueueRepository;
import com.clinic.dental_clinic.repository.DentistRepository;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.service.AppointmentQueueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AppointmentQueueServiceImpl implements AppointmentQueueService {

    private final AppointmentQueueRepository queueRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final PricingContext pricingContext;
    private final QueueSubject queueSubject;

    public AppointmentQueueServiceImpl(AppointmentQueueRepository queueRepository,
                                       PatientRepository patientRepository,
                                       DentistRepository dentistRepository,
                                       PricingContext pricingContext,
                                       QueueSubject queueSubject) {
        this.queueRepository = queueRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.pricingContext = pricingContext;
        this.queueSubject = queueSubject;
    }

    @Override
    @Transactional
    public QueueResponse createQueue(QueueBookingRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));

        Dentist dentist = dentistRepository.findById(request.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + request.getDentistId()));

        Double finalPrice = pricingContext.calculateFinalPrice(patient.getCoverageType(), request.getBasePrice());

        AppointmentQueue queue = new AppointmentQueue();
        queue.setQueueNumber("Q-" + System.currentTimeMillis() % 10000);
        queue.setServiceType(request.getServiceType());
        queue.setBasePrice(request.getBasePrice());
        queue.setFinalPrice(finalPrice);
        queue.setStatus(QueueStatus.WAITING);
        queue.setCreatedAt(LocalDateTime.now());
        queue.setPatient(patient);
        queue.setDentist(dentist);

        AppointmentQueue savedQueue = queueRepository.save(queue);
        queueSubject.notifyObservers(savedQueue.getQueueNumber(), savedQueue.getStatus().name());
        return AppointmentQueueMapper.toResponse(savedQueue);
    }

    @Override
    @Transactional(readOnly = true)
    public QueueResponse getQueueById(Long id) {
        AppointmentQueue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment queue not found with id: " + id));
        return AppointmentQueueMapper.toResponse(queue);
    }

    @Override
    @Transactional(readOnly = true)
    public Long suggestDentistId(String serviceType) {
        return dentistRepository.findAll().stream()
                .filter(dentist -> "EXTRACTION".equalsIgnoreCase(serviceType)
                        && dentist.getSpecialization() != null
                        && dentist.getSpecialization().contains("ศัลยศาสตร์ช่องปาก"))
                .findFirst()
                .orElseGet(() -> dentistRepository.findAll().stream()
                        .min(Comparator.comparing(Dentist::getId))
                        .orElseThrow(() -> new ResourceNotFoundException("No dentist is available")))
                .getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QueueResponse> getAllQueues(Pageable pageable) {
        Page<AppointmentQueue> allQueues = queueRepository.findAll(pageable);
        List<AppointmentQueue> activeQueues = allQueues.getContent().stream()
                .filter(queue -> queue.getStatus() != QueueStatus.CANCELLED)
                .toList();
        return new org.springframework.data.domain.PageImpl<>(
                activeQueues.stream().map(AppointmentQueueMapper::toResponse).toList(),
                pageable,
                activeQueues.size()
        );
    }

    @Override
    @Transactional
    public QueueResponse updateQueueStatus(Long id, String statusStr) {
        AppointmentQueue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment queue not found with id: " + id));

        try {
            QueueStatus targetStatus = QueueStatus.valueOf(statusStr.toUpperCase());
            queue.setStatus(targetStatus);
            AppointmentQueue updatedQueue = queueRepository.save(queue);
            queueSubject.notifyObservers(updatedQueue.getQueueNumber(), updatedQueue.getStatus().name());
            return AppointmentQueueMapper.toResponse(updatedQueue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid queue status: " + statusStr);
        }
    }

    @Override
    @Transactional
    public QueueResponse cancelQueue(Long id) {
        AppointmentQueue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment queue not found with id: " + id));

        if (queue.getStatus() == QueueStatus.COMPLETED) {
            throw new IllegalStateException("Completed queue cannot be cancelled.");
        }

        if (queue.getStatus() == QueueStatus.CANCELLED) {
            return AppointmentQueueMapper.toResponse(queue);
        }

        queue.setStatus(QueueStatus.CANCELLED);
        AppointmentQueue updatedQueue = queueRepository.save(queue);
        queueRepository.flush();
        queueSubject.notifyObservers(updatedQueue.getQueueNumber(), QueueStatus.CANCELLED.name());
        return AppointmentQueueMapper.toResponse(updatedQueue);
    }

    // --- ส่วนที่เพิ่มใหม่สำหรับ Hard Delete ---
    @Override
    @Transactional
    public void deleteQueue(Long id) {
        AppointmentQueue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment queue not found with id: " + id));

        queueRepository.delete(queue);
        queueRepository.flush();
        queueSubject.notifyObservers(queue.getQueueNumber(), QueueStatus.CANCELLED.name());
    }
}