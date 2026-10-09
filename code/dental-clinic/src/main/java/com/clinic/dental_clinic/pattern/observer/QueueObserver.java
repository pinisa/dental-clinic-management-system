package com.clinic.dental_clinic.pattern.observer;

public interface QueueObserver {
    void onQueueStatusChanged(String queueNumber, String status);
}