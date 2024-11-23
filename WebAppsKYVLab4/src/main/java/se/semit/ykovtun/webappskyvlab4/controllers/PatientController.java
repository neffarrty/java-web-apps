package se.semit.ykovtun.webappskyvlab4.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab4.entities.Patient;
import se.semit.ykovtun.webappskyvlab4.services.department.HospitalDepartmentService;
import se.semit.ykovtun.webappskyvlab4.services.patient.PatientServiceImpl;

import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Controller
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private final PatientServiceImpl patientService;
    private final HospitalDepartmentService departmentService;

    @GetMapping("/")
    public String getPatients(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/new")
    public String createPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("departments", departmentService.findAll());
        return "patient/new";
    }

    @GetMapping("/{id}/edit")
    public String editPatientForm(@PathVariable int id, Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("patient", patientService.findById(id));
        return "patient/edit";
    }

    @PostMapping("/")
    public String addPatient(
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            System.out.println("hello");
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("patient", patient);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            return "patient/new";
        }

        try {
            this.patientService.create(patient);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("patient", patient);
            model.addAttribute("error", e.getMessage());
            return "patient/new";
        }

        return "redirect:/patients/";
    }

    @PatchMapping("/{id}")
    public String editPatient(
        @PathVariable long id,
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("patient", patient);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            return "patient/edit";
        }

        try {
            this.patientService.update(id, patient);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("patient", patient);
            model.addAttribute("error", e.getMessage());
            return "patient/edit";
        }

        return "redirect:/patients/";
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable long id) {
        this.patientService.delete(id);
        return "redirect:/patients/";
    }
}
