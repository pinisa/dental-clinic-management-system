package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.dto.request.QueueBookingRequest;
import com.clinic.dental_clinic.dto.response.QueueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentQueueService {
    QueueResponse createQueue(QueueBookingRequest request);
    QueueResponse getQueueById(Long id);
    Page<QueueResponse> getAllQueues(Pageable pageable);
    Long suggestDentistId(String serviceType);
    QueueResponse updateQueueStatus(Long id, String statusStr);
    QueueResponse cancelQueue(Long id);
}