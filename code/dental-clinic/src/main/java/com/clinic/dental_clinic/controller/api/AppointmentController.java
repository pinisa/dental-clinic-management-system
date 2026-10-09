package com.clinic.dental_clinic.controller.api;

import com.clinic.dental_clinic.dto.request.AppointmentRequest;
import com.clinic.dental_clinic.dto.response.AppointmentResponse;
import com.clinic.dental_clinic.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) { this.appointmentService = appointmentService; }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.create(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(appointmentService.getById(id)); }
    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> getAll(@PageableDefault(size = 10, sort = "appointmentDateTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAll(pageable));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { appointmentService.delete(id); return ResponseEntity.noContent().build(); }
}
