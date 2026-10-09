package com.clinic.dental_clinic.pattern.observer;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationObserver extends NotificationTemplate implements QueueObserver {
    @Override
    public void onQueueStatusChanged(String queueNumber, String status) {
        notifyQueueChange(queueNumber, status);
    }
    @Override
    protected String buildMessage(String queueNumber, String status) {
        return "Queue " + queueNumber + ": Current Status is " + status;
    }
    @Override
    protected void send(String message) {
        System.out.println("[SMS NOTIFICATION] Sent SMS - " + message);
    }
}
