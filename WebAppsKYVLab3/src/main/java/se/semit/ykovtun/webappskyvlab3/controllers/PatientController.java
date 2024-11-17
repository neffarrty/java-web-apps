package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.services.department.HospitalDepartmentService;
import se.semit.ykovtun.webappskyvlab3.services.patient.PatientServiceImpl;

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
        List<Patient> patients = this.patientService.findAll();
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
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            model.addAttribute("patient", patient);
            return "patient/new";
        }
        this.patientService.create(patient);
        return "redirect:/patients/";
    }

    @PatchMapping("/{id}")
    public String editPatient(
        @PathVariable long id,
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            model.addAttribute("patient", patient);
            return "patient/edit";
        }
        this.patientService.update(id, patient);
        return "redirect:/patients/";
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable long id) {
        this.patientService.delete(id);
        return "redirect:/patients/";
    }
}
