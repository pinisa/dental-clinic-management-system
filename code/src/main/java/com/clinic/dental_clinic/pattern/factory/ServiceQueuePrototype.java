package com.clinic.dental_clinic.pattern.factory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
public class ServiceQueuePrototype {

    private String sessionTrackerId = UUID.randomUUID().toString();
    private long timestamp = System.currentTimeMillis();

    public ServiceQueuePrototype() {
    }

    public String getSessionTrackerId() {
        return sessionTrackerId;
    }

    public void setSessionTrackerId(String sessionTrackerId) {
        this.sessionTrackerId = sessionTrackerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}