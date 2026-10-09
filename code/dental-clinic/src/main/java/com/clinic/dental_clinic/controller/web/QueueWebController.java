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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("queueCount", queueService.getAllQueues(pageable).getTotalElements());
        return "queues/list";
    }

    @GetMapping("/new")
    public String showBookingForm(Model model) {
        WebQueueBookingRequest request = new WebQueueBookingRequest();
        request.setBasePrice(900.0);
        model.addAttribute("queueRequest", request);
        model.addAttribute("patientTypes", PatientType.values());
        model.addAttribute("queueCount", queueService.getAllQueues(Pageable.unpaged()).getTotalElements());
        return "queues/form";
    }

    @PostMapping
    public String processBooking(@Valid @ModelAttribute("queueRequest") WebQueueBookingRequest request,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        model.addAttribute("patientTypes", PatientType.values());

        if (result.hasErrors()) {
            model.addAttribute("queueCount", queueService.getAllQueues(Pageable.unpaged()).getTotalElements());
            return "queues/form";
        }

        Long patientId = patientService.findByPhone(request.getPhone())
                .map(PatientResponse::getId)
                .orElseGet(() -> patientService.createPatient(new PatientRequest(
                        request.getPatientName(),
                        request.getPhone(),
                        request.getEmail(),
                        request.getCoverageType(),
                        null,
                        null,
                        request.getEmergencyContact()
                )).getId());

        Long dentistId = queueService.suggestDentistId(request.getServiceType());

        QueueBookingRequest queueRequest = new QueueBookingRequest(
                patientId,
                dentistId,
                request.getServiceType(),
                request.getBasePrice()
        );

        QueueResponse created = queueService.createQueue(queueRequest);
        redirectAttributes.addFlashAttribute("successMessage", "จองคิวสำเร็จ หมายเลขคิว " + created.getQueueNumber());
        return "redirect:/queues/success/" + created.getId();
    }

    @GetMapping("/success/{id}")
    public String bookingSuccess(@PathVariable Long id, Model model) {
        model.addAttribute("queue", queueService.getQueueById(id));
        return "queues/success";
    }

    @PostMapping("/{id}/cancel")
    public String cancelQueue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        QueueResponse queue = queueService.cancelQueue(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "ยกเลิกคิวสำเร็จ หมายเลขคิว " + queue.getQueueNumber());
        return "redirect:/queues";
    }

    @GetMapping("/status")
    public String queueStatusFragment(Model model, Pageable pageable) {
        model.addAttribute("queues", queueService.getAllQueues(pageable));
        model.addAttribute("queueCount", queueService.getAllQueues(pageable).getTotalElements());
        return "queues/list :: queueTable";
    }
}
