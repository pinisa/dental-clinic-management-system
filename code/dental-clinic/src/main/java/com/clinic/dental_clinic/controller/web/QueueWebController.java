package com.clinic.dental_clinic.controller.web;

import com.clinic.dental_clinic.domain.enums.PatientType;
import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.request.QueueBookingRequest;
import com.clinic.dental_clinic.dto.request.WebQueueBookingRequest;
import com.clinic.dental_clinic.dto.response.PatientResponse;
import com.clinic.dental_clinic.dto.response.QueueResponse;
import com.clinic.dental_clinic.service.AppointmentQueueService;
import com.clinic.dental_clinic.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/queues")
public class QueueWebController {

    private final AppointmentQueueService queueService;
    private final PatientService patientService;

    public QueueWebController(AppointmentQueueService queueService,
                               PatientService patientService) {
        this.queueService = queueService;
        this.patientService = patientService;
    }

    @GetMapping
    public String queueList(Model model, Pageable pageable) {
        model.addAttribute("queues", queueService.getAllQueues(pageable));
        return "queues/list";
    }

    @GetMapping("/new")
    public String showBookingForm(Model model) {
        WebQueueBookingRequest request = new WebQueueBookingRequest();
        request.setBasePrice(900.0);
        model.addAttribute("queueRequest", request);
        model.addAttribute("patientTypes", PatientType.values());
        return "queues/form";
    }

    @PostMapping
    public String processBooking(@Valid @ModelAttribute("queueRequest") WebQueueBookingRequest request,
                                 BindingResult result,
                                 Model model) {
        model.addAttribute("patientTypes", PatientType.values());

        if (result.hasErrors()) {
            return "queues/form";
        }

        // If this phone already belongs to a patient, reuse that patient.
        // Otherwise create a new patient from the booking form.
        Long patientId = patientService.findByPhone(request.getPhone())
                .map(PatientResponse::getId)
                .orElseGet(() -> patientService.createPatient(new PatientRequest(
                        request.getPatientName(),
                        request.getPhone(),
                        request.getCoverageType(),
                        null,
                        null,
                        request.getEmergencyContact()
                )).getId());

        // The customer does not need to choose an internal dentist ID.
        // The service assigns a suitable dentist automatically.
        Long dentistId = queueService.suggestDentistId(request.getServiceType());

        QueueBookingRequest queueRequest = new QueueBookingRequest(
                patientId,
                dentistId,
                request.getServiceType(),
                request.getBasePrice()
        );

        QueueResponse created = queueService.createQueue(queueRequest);
        return "redirect:/queues/success/" + created.getId();
    }

    @GetMapping("/success/{id}")
    public String bookingSuccess(@PathVariable Long id, Model model) {
        model.addAttribute("queue", queueService.getQueueById(id));
        return "queues/success";
    }
}
