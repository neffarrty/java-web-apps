package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.services.HospitalDepartmentService;
import se.semit.ykovtun.webappskyvlab3.services.PatientService;

import java.util.List;

@Controller
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final HospitalDepartmentService departmentService;

    @GetMapping("/")
    public String getPatients(Model model) {
        List<Patient> patients = this.patientService.findAll();
        model.addAttribute("patients", patients);
        return "patient/patients";
    }

    @GetMapping("/{id}")
    public String getPatientById(@PathVariable int id, Model model) {
        Patient patient = this.patientService.findById(id);
        model.addAttribute("patient", patient);
        return "patient/patient";
    }

    @GetMapping("/new")
    public String createPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "patient/patient-new";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable int id, Model model) {
        Patient patient = this.patientService.findById(id);
        model.addAttribute("patient", patient);
        return "patient/patient-edit";
    }


    @PostMapping("/")
    public String addPatient(
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.patient", result);
            model.addAttribute("patient", patient);
            return "patient/patients";
        }
        this.patientService.create(patient);
        return "redirect:/patients/";
    }

    @PatchMapping("/{id}")
    public String editPatient(
        @PathVariable long id,
        @Valid @ModelAttribute Patient patient,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.patient", result);
            model.addAttribute("patient", patient);
            return "patient/patient-edit";
        }
        this.patientService.update(id, patient);
        return "redirect:/patients/";
    }
}
