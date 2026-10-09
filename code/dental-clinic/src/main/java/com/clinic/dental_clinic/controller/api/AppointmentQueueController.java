package com.clinic.dental_clinic.controller.api;

import com.clinic.dental_clinic.dto.request.QueueBookingRequest;
import com.clinic.dental_clinic.dto.response.QueueResponse;
import com.clinic.dental_clinic.service.AppointmentQueueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/queues")
public class AppointmentQueueController {

    private final AppointmentQueueService queueService;

    public AppointmentQueueController(AppointmentQueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public ResponseEntity<QueueResponse> createQueue(@Valid @RequestBody QueueBookingRequest request) {
        QueueResponse response = queueService.createQueue(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueueResponse> getQueueById(@PathVariable Long id) {
        QueueResponse response = queueService.getQueueById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<QueueResponse>> getAllQueues(@PageableDefault(size = 10) Pageable pageable) {
        Page<QueueResponse> responses = queueService.getAllQueues(pageable);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QueueResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        QueueResponse response = queueService.updateQueueStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<QueueResponse> cancelQueue(@PathVariable Long id) {
        QueueResponse response = queueService.cancelQueue(id);
        return ResponseEntity.ok(response);
    }
}