package com.clinic.dental_clinic.controller.web;

import com.clinic.dental_clinic.domain.entity.Appointment;
import com.clinic.dental_clinic.domain.entity.AppointmentQueue;
import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.entity.TreatmentRecord;
import com.clinic.dental_clinic.domain.enums.QueueStatus;
import com.clinic.dental_clinic.exception.ResourceConflictException;
import com.clinic.dental_clinic.exception.ResourceNotFoundException;
import com.clinic.dental_clinic.dto.request.AppointmentRequest;
import com.clinic.dental_clinic.dto.request.QueueBookingRequest;
import com.clinic.dental_clinic.dto.request.TreatmentRecordRequest;
import com.clinic.dental_clinic.dto.response.QueueResponse;
import com.clinic.dental_clinic.repository.AppointmentQueueRepository;
import com.clinic.dental_clinic.repository.AppointmentRepository;
import com.clinic.dental_clinic.repository.DentistRepository;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.repository.PatientProfileRepository;
import com.clinic.dental_clinic.repository.TreatmentRecordRepository;
import com.clinic.dental_clinic.service.AppointmentQueueService;
import com.clinic.dental_clinic.service.AppointmentService;
import com.clinic.dental_clinic.service.TreatmentRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {
    private final PatientRepository patientRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final DentistRepository dentistRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentQueueRepository queueRepository;
    private final TreatmentRecordRepository treatmentRepository;
    private final AppointmentService appointmentService;
    private final AppointmentQueueService queueService;
    private final TreatmentRecordService treatmentService;

    public AdminController(PatientRepository patientRepository, PatientProfileRepository patientProfileRepository,
                           DentistRepository dentistRepository, AppointmentRepository appointmentRepository,
                           AppointmentQueueRepository queueRepository, TreatmentRecordRepository treatmentRepository,
                           AppointmentService appointmentService, AppointmentQueueService queueService,
                           TreatmentRecordService treatmentService) {
        this.patientRepository = patientRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.dentistRepository = dentistRepository;
        this.appointmentRepository = appointmentRepository;
        this.queueRepository = queueRepository;
        this.treatmentRepository = treatmentRepository;
        this.appointmentService = appointmentService;
        this.queueService = queueService;
        this.treatmentService = treatmentService;
    }

    @GetMapping({"/", "/admin"})
    public String dashboard(Model model) {
        model.addAttribute("patientCount", patientRepository.count());
        model.addAttribute("profileCount", patientProfileRepository.count());
        model.addAttribute("dentistCount", dentistRepository.count());
        model.addAttribute("appointmentCount", appointmentRepository.count());
        model.addAttribute("queueCount", queueRepository.count());
        model.addAttribute("treatmentCount", treatmentRepository.count());
        model.addAttribute("waitingCount", queueRepository.findByStatus(QueueStatus.WAITING).size());
        // Sum only completed queues; cancelled/waiting queues are not counted as realized revenue.
        double completedRevenue = queueRepository.findAll().stream()
                .filter(queue -> queue.getStatus() == QueueStatus.COMPLETED)
                .map(AppointmentQueue::getFinalPrice)
                .filter(java.util.Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
        model.addAttribute("completedRevenue", completedRevenue);
        model.addAttribute("recentQueues", queueService.getAllQueues(PageRequest.of(0, 6)).getContent());
        model.addAttribute("recentAppointments", appointmentService.getAll(PageRequest.of(0, 6)).getContent());
        return "index";
    }

    @GetMapping("/admin/appointments")
    public String appointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAll(PageRequest.of(0, 500)).getContent());
        return "admin/appointments";
    }

    @GetMapping("/admin/appointments/new")
    public String newAppointment(Model model) {
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        addAppointmentChoices(model);
        return "admin/appointment-form";
    }

    @PostMapping("/admin/appointments")
    public String saveAppointment(@Valid @ModelAttribute("appointmentRequest") AppointmentRequest request,
                                  BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            addAppointmentChoices(model);
            return "admin/appointment-form";
        }
        try {
            appointmentService.create(request);
            redirect.addFlashAttribute("successMessage", "บันทึกนัดหมายแล้ว");
            return "redirect:/admin/appointments";
        } catch (RuntimeException ex) {
            addAppointmentChoices(model);
            model.addAttribute("errorMessage", safeMessage(ex));
            return "admin/appointment-form";
        }
    }

    @PostMapping("/admin/appointments/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirect) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setStatus(QueueStatus.CANCELLED);
        appointmentRepository.save(appointment);
        redirect.addFlashAttribute("successMessage", "ยกเลิกนัดหมายแล้ว");
        return "redirect:/admin/appointments";
    }

    @GetMapping("/admin/queues/new")
    public String newQueue(Model model) {
        model.addAttribute("queueRequest", new QueueBookingRequest());
        addQueueChoices(model);
        return "admin/queue-form";
    }

    @PostMapping("/admin/queues")
    public String saveQueue(@Valid @ModelAttribute("queueRequest") QueueBookingRequest request,
                            BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            addQueueChoices(model);
            return "admin/queue-form";
        }
        try {
            QueueResponse created = queueService.createQueue(request);
            redirect.addFlashAttribute("successMessage", "สร้างคิว " + created.getQueueNumber() + " แล้ว");
            return "redirect:/queues";
        } catch (RuntimeException ex) {
            addQueueChoices(model);
            model.addAttribute("errorMessage", safeMessage(ex));
            return "admin/queue-form";
        }
    }

    @PostMapping("/admin/queues/{id}/status")
    public String updateQueueStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirect) {
        queueService.updateQueueStatus(id, status);
        redirect.addFlashAttribute("successMessage", "อัปเดตสถานะคิวแล้ว");
        return "redirect:/queues";
    }

    @GetMapping("/admin/treatments")
    public String treatments(Model model) {
        model.addAttribute("treatments", treatmentRepository.findAll());
        return "admin/treatments";
    }

    @GetMapping("/admin/treatments/new")
    public String newTreatment(Model model) {
        model.addAttribute("treatmentRequest", new TreatmentRecordRequest());
        addTreatmentChoices(model);
        return "admin/treatment-form";
    }

    @PostMapping("/admin/treatments")
    public String saveTreatment(@Valid @ModelAttribute("treatmentRequest") TreatmentRecordRequest request,
                                BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            addTreatmentChoices(model);
            return "admin/treatment-form";
        }
        try {
            treatmentService.createTreatmentRecord(request);
            redirect.addFlashAttribute("successMessage", "บันทึกประวัติการรักษาแล้ว และปิดคิวที่เกี่ยวข้องแล้ว");
            return "redirect:/admin/treatments";
        } catch (RuntimeException ex) {
            addTreatmentChoices(model);
            model.addAttribute("errorMessage", safeMessage(ex));
            return "admin/treatment-form";
        }
    }

    @GetMapping("/admin/dentists")
    public String dentists(Model model) {
        model.addAttribute("dentists", dentistRepository.findAll());
        return "admin/dentists";
    }

    private void addAppointmentChoices(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("dentists", dentistRepository.findAll());
    }
    private void addQueueChoices(Model model) { addAppointmentChoices(model); }
    private void addTreatmentChoices(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("dentists", dentistRepository.findAll());
        List<AppointmentQueue> availableQueues = queueRepository.findAll().stream()
                .filter(q -> q.getStatus() != QueueStatus.CANCELLED)
                .filter(q -> q.getPatient() != null && q.getDentist() != null)
                .filter(q -> !treatmentRepository.existsByAppointmentQueueId(q.getId()))
                .toList();
        model.addAttribute("queues", availableQueues);
    }
    private String safeMessage(RuntimeException ex) {
        if (ex instanceof IllegalArgumentException || ex instanceof ResourceConflictException || ex instanceof ResourceNotFoundException) {
            if (ex.getMessage() != null && !ex.getMessage().isBlank()) return ex.getMessage();
        }
        return "ดำเนินการไม่สำเร็จ กรุณาตรวจสอบข้อมูลแล้วลองใหม่";
    }
}
