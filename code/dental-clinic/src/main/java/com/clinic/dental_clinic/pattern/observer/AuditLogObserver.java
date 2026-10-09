package com.clinic.dental_clinic.pattern.observer;

import org.springframework.stereotype.Component;

@Component
public class AuditLogObserver extends NotificationTemplate implements QueueObserver {
    @Override
    public void onQueueStatusChanged(String queueNumber, String status) {
        notifyQueueChange(queueNumber, status);
    }
    @Override
    protected String buildMessage(String queueNumber, String status) {
        return "Queue " + queueNumber + " updated status to: " + status;
    }
    @Override
    protected void send(String message) {
        System.out.println("[AUDIT LOG] " + message);
    }
}
