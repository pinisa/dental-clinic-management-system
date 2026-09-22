package com.clinic.dental_clinic.pattern.observer;

import com.clinic.dental_clinic.model.AppointmentQueue;

public interface QueueObserver {
    void onQueueStatusChanged(AppointmentQueue queue);
}