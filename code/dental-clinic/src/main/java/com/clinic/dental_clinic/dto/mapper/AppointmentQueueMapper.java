package com.clinic.dental_clinic.dto.mapper;

import com.clinic.dental_clinic.domain.entity.AppointmentQueue;
import com.clinic.dental_clinic.dto.response.QueueResponse;

public class AppointmentQueueMapper {

    public static QueueResponse toResponse(AppointmentQueue queue) {
        return new QueueResponse(
                queue.getId(),
                queue.getQueueNumber(),
                queue.getServiceType(),
                queue.getBasePrice(),
                queue.getFinalPrice(),
                queue.getStatus(),
                queue.getCreatedAt(),
                queue.getPatient() != null ? queue.getPatient().getId() : null,
                queue.getPatient() != null ? queue.getPatient().getName() : null,
                queue.getDentist() != null ? queue.getDentist().getId() : null,
                queue.getDentist() != null ? queue.getDentist().getName() : null
        );
    }
}