package com.clinic.dental_clinic.pattern.observer;

import com.clinic.dental_clinic.model.AppointmentQueue;
import com.clinic.dental_clinic.service.SmsNotificationService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationObserver implements QueueObserver {

    private final SmsNotificationService smsService;

    public SmsNotificationObserver(SmsNotificationService smsService) {
        this.smsService = smsService;
    }

    @Override
    public void onQueueStatusChanged(AppointmentQueue queue) {
        if (queue.getPatient() != null && queue.getPatient().getPhone() != null) {
            String message = String.format("คิวหมายเลข %s ของคุณ เปลี่ยนสถานะเป็น: %s", 
                    queue.getQueueNumber(), queue.getStatus());
            
            smsService.sendQueueNotification(queue.getPatient().getPhone(), message);
        }
    }
}