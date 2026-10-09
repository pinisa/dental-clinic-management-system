package com.clinic.dental_clinic.controller.api;

import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.response.AppointmentResponse;
import com.clinic.dental_clinic.service.AppointmentService;
import com.clinic.dental_clinic.dto.response.PatientResponse;
import com.clinic.dental_clinic.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PatientController(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.createPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        PatientResponse response = patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAllPatients(@PageableDefault(size = 10) Pageable pageable) {
        Page<PatientResponse> responses = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<Page<AppointmentResponse>> getPatientAppointments(@PathVariable Long id, @PageableDefault(size = 10, sort = "appointmentDateTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getByPatientId(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.updatePatient(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}