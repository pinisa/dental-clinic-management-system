package com.clinic.dental_clinic.controller.web;

import com.clinic.dental_clinic.domain.enums.PatientType;
import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.dto.response.PatientResponse;
import com.clinic.dental_clinic.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class PatientWebController {
    private final PatientService patientService;
    public PatientWebController(PatientService patientService) { this.patientService = patientService; }

    @GetMapping
    public String patientList(Model model) {
        model.addAttribute("patients", patientService.getAllPatients(PageRequest.of(0, 500)));
        return "patients/list";
    }

    @GetMapping("/new")
    public String newPatient(Model model) {
        model.addAttribute("patientRequest", new PatientRequest());
        model.addAttribute("patientTypes", PatientType.values());
        model.addAttribute("patientId", null);
        return "patients/form";
    }

    @GetMapping("/{id}/edit")
    public String editPatient(@PathVariable Long id, Model model) {
        PatientResponse p = patientService.getPatientById(id);
        PatientRequest request = new PatientRequest(p.getName(), p.getPhone(), p.getEmail(), p.getCoverageType(),
                p.getMedicalHistory(), p.getAllergies(), p.getEmergencyContact());
        model.addAttribute("patientRequest", request);
        model.addAttribute("patientTypes", PatientType.values());
        model.addAttribute("patientId", id);
        return "patients/form";
    }

    @PostMapping
    public String savePatient(@RequestParam(required = false) Long patientId,
                              @Valid @ModelAttribute("patientRequest") PatientRequest request,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("patientTypes", PatientType.values());
            model.addAttribute("patientId", patientId);
            return "patients/form";
        }
        try {
            if (patientId == null) patientService.createPatient(request);
            else patientService.updatePatient(patientId, request);
            redirectAttributes.addFlashAttribute("successMessage", patientId == null ? "เพิ่มข้อมูลผู้ป่วยแล้ว" : "แก้ไขข้อมูลผู้ป่วยแล้ว");
            return "redirect:/patients";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("patientTypes", PatientType.values());
            model.addAttribute("patientId", patientId);
            model.addAttribute("errorMessage", ex.getMessage());
            return "patients/form";
        }
    }
}
