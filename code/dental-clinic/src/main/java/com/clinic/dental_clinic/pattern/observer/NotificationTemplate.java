package com.clinic.dental_clinic.pattern.observer;

public abstract class NotificationTemplate {
    public final void notifyQueueChange(String queueNumber, String status) {
        String message = buildMessage(queueNumber, status);
        send(message);
    }
    protected abstract String buildMessage(String queueNumber, String status);
    protected abstract void send(String message);
}
