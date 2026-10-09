package com.clinic.dental_clinic.controller.api;

import com.clinic.dental_clinic.dto.request.TreatmentRecordRequest;
import com.clinic.dental_clinic.dto.response.TreatmentRecordResponse;
import com.clinic.dental_clinic.service.TreatmentRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/treatments")
public class TreatmentController {

    private final TreatmentRecordService treatmentRecordService;

    public TreatmentController(TreatmentRecordService treatmentRecordService) {
        this.treatmentRecordService = treatmentRecordService;
    }

    @PostMapping
    public ResponseEntity<TreatmentRecordResponse> createRecord(@Valid @RequestBody TreatmentRecordRequest request) {
        TreatmentRecordResponse response = treatmentRecordService.createTreatmentRecord(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentRecordResponse> getRecordById(@PathVariable Long id) {
        TreatmentRecordResponse response = treatmentRecordService.getTreatmentRecordById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<TreatmentRecordResponse>> getRecordsByPatientId(
            @PathVariable Long patientId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TreatmentRecordResponse> responses = treatmentRecordService.getTreatmentRecordsByPatientId(patientId, pageable);
        return ResponseEntity.ok(responses);
    }
}