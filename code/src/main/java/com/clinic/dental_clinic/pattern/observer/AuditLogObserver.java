package com.clinic.dental_clinic.pattern.observer;

import com.clinic.dental_clinic.model.AppointmentQueue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditLogObserver implements QueueObserver {

    @Override
    public void onQueueStatusChanged(AppointmentQueue queue) {
        System.out.printf("[AUDIT LOG] เวลา: %s | คิว: %s | สถานะใหม่: %s | ผู้ป่วย: %s%n",
                LocalDateTime.now(),
                queue.getQueueNumber(),
                queue.getStatus(),
                queue.getPatient() != null ? queue.getPatient().getName() : "N/A");
    }
}