package com.clinic.dental_clinic.pattern.observer;

import com.clinic.dental_clinic.model.AppointmentQueue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class QueueSubject {

    // Spring จะรวบรวมคลาสทั้งหมดที่ implements QueueObserver มาใส่ใน List นี้ให้อัตโนมัติ
    private final List<QueueObserver> observers;

    public QueueSubject(List<QueueObserver> observers) {
        this.observers = observers;
    }

    public void notifyObservers(AppointmentQueue queue) {
        for (QueueObserver observer : observers) {
            observer.onQueueStatusChanged(queue);
        }
    }
}