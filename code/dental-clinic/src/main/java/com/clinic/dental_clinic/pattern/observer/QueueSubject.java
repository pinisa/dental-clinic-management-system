package com.clinic.dental_clinic.pattern.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueueSubject {

    private final List<QueueObserver> observers = new ArrayList<>();

    public QueueSubject(List<QueueObserver> observerList) {
        this.observers.addAll(observerList);
    }

    public void addObserver(QueueObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(QueueObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String queueNumber, String status) {
        for (QueueObserver observer : observers) {
            observer.onQueueStatusChanged(queueNumber, status);
        }
    }
}