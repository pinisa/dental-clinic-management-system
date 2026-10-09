package com.clinic.dental_clinic.controller.web;

import com.clinic.dental_clinic.service.PatientService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class PatientWebController {

    private final PatientService patientService;

    public PatientWebController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String patientList(Model model, Pageable pageable) {
        model.addAttribute("patients", patientService.getAllPatients(pageable));
        return "patients/list";
    }
}