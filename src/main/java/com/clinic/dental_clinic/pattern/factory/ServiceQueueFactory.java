package com.clinic.dental_clinic.pattern.factory;

import com.clinic.dental_clinic.model.AppointmentQueue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ServiceQueueFactory {

    public AppointmentQueue createQueue(String serviceType, String queueSeq) {
        AppointmentQueue queue = new AppointmentQueue();
        queue.setServiceType(serviceType);

        switch (serviceType.toUpperCase()) {
            case "CHECKUP":
                queue.setQueueNumber("A-" + queueSeq);
                queue.setBasePrice(500.0);
                break;
            case "SCALING":
                queue.setQueueNumber("B-" + queueSeq);
                queue.setBasePrice(1200.0);
                break;
            case "WISDOM_TOOTH":
                queue.setQueueNumber("C-" + queueSeq);
                queue.setBasePrice(3500.0);
                break;
            default:
                throw new IllegalArgumentException("Unknown service type: " + serviceType);
        }
        return queue;
    }
}