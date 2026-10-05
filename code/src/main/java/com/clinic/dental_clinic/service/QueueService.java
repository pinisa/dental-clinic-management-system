package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.model.*;
import com.clinic.dental_clinic.pattern.factory.ServiceQueueFactory;
import com.clinic.dental_clinic.pattern.observer.QueueSubject;
import com.clinic.dental_clinic.pattern.strategy.PricingContext;
import com.clinic.dental_clinic.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QueueService {

    private final AppointmentQueueRepository queueRepository;
    private final PatientRepository patientRepository;
    private final ServiceQueueFactory queueFactory;
    private final PricingContext pricingContext;
    private final QueueSubject queueSubject;

    public QueueService(AppointmentQueueRepository queueRepository,
                        PatientRepository patientRepository,
                        ServiceQueueFactory queueFactory,
                        PricingContext pricingContext,
                        QueueSubject queueSubject) {
        this.queueRepository = queueRepository;
        this.patientRepository = patientRepository;
        this.queueFactory = queueFactory;
        this.pricingContext = pricingContext;
        this.queueSubject = queueSubject;
    }

    @Transactional
    public AppointmentQueue bookAppointment(String name, String phone, String coverageType, String serviceType) {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setPhone(phone);
        patient.setCoverageType(coverageType);
        patientRepository.save(patient);

        long countToday = queueRepository.count() + 1;

        // เรียกใช้ Factory Pattern
        AppointmentQueue queue = queueFactory.createQueue(serviceType, String.format("%03d", countToday));

        // เรียกใช้ Strategy Pattern คำนวณราคา
        double finalPrice = pricingContext.calculate(coverageType, queue.getBasePrice());
        queue.setFinalPrice(finalPrice);
        queue.setPatient(patient);

        AppointmentQueue savedQueue = queueRepository.save(queue);

        // เรียกใช้ Observer Pattern แจ้งเตือนทุกผู้สังเกตการณ์
        queueSubject.notifyObservers(savedQueue);

        return savedQueue;
    }

    @Transactional
    public void advanceQueueState(Long queueId) {
        AppointmentQueue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new IllegalArgumentException("Queue not found with ID: " + queueId));

        if (queue.getStatus() == QueueStatus.WAITING) {
            queue.setStatus(QueueStatus.IN_TREATMENT);
        } else if (queue.getStatus() == QueueStatus.IN_TREATMENT) {
            queue.setStatus(QueueStatus.COMPLETED);
        }

        AppointmentQueue updatedQueue = queueRepository.save(queue);

        // แจ้งเตือนเมื่อมีการอัปเดตสถานะคิว
        queueSubject.notifyObservers(updatedQueue);
    }

    public List<AppointmentQueue> getAllQueues() {
        return queueRepository.findAll();
    }
}