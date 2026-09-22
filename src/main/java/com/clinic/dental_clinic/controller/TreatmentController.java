package com.clinic.dental_clinic.controller;

import com.clinic.dental_clinic.model.TreatmentRecord;
import com.clinic.dental_clinic.service.TreatmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    // API สำหรับบันทึกการรักษา
    @PostMapping("/record")
    public ResponseEntity<TreatmentRecord> recordTreatment(@RequestParam Long patientId,
                                                               @RequestParam Long dentistId,
                                                               @RequestParam String diagnosisNote,
                                                               @RequestParam Double totalCost) {
        TreatmentRecord record = treatmentService.recordTreatment(patientId, dentistId, diagnosisNote, totalCost);
        return ResponseEntity.ok(record);
    }
}