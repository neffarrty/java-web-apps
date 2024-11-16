package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.services.HospitalDepartmentService;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Controller
@RequestMapping(path = "/hospital-departments")
@AllArgsConstructor
public class HospitalDepartmentController {
    private final HospitalDepartmentService service;

    @GetMapping("/")
    public String listHospitalDepartments(Model model) {
        model.addAttribute("departments", service.findAll());
        return "hospital-department/list";
    }

    @GetMapping("/{id}/patients")
    public String listPatients(@PathVariable long id, Model model) {
        model.addAttribute("department", service.findById(id));
        return "hospital-department/patients";
    }

    @GetMapping("/{id}/patients/new")
    public String newPatientForm(@PathVariable long id, Model model) {
        HospitalDepartment department = service.findById(id);
        Patient patient = new Patient();
        patient.setDepartment(department);
        model.addAttribute("department", department);
        model.addAttribute("patient", patient);
        return "hospital-department/new-patient";
    }

    @GetMapping("/new")
    public String newHospitalDepartmentForm(Model model) {
        model.addAttribute("department", new HospitalDepartment());
        return "hospital-department/new";
    }

    @GetMapping("/{id}/edit")
    public String editHospitalDepartmentForm(@PathVariable long id, Model model) {
        model.addAttribute("department", service.findById(id));
        return "hospital-department/edit";
    }

    @PostMapping("/")
    public String createHospitalDepartment(
        @Valid @ModelAttribute HospitalDepartment department,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "department", result);
            model.addAttribute("department", department);
            return "hospital-department/new";
        }

        try {
            service.create(department);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("department", department);
            return "hospital-department/new";
        }

        return "redirect:/hospital-departments/";
    }

    @PostMapping("/{id}/patients")
    public String addPatient(
        @PathVariable long id,
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            HospitalDepartment department = this.service.findById(id);
            model.addAttribute("department", department);
            model.addAttribute("patient", patient);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            return "hospital-department/new-patient";
        }
        service.addPatient(id, patient);
        return "redirect:/hospital-departments/" + id + "/patients";
    }

    @PatchMapping("/{id}")
    public String updateHospitalDepartment(
        @PathVariable long id,
        @Valid @ModelAttribute HospitalDepartment department,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "department", result);
            model.addAttribute("department", department);
            return "hospital-department/edit";
        }

        try {
            service.update(id, department);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("department", department);
            return "hospital-department/edit";
        }

        return "redirect:/hospital-departments/";
    }

    @DeleteMapping("/{id}")
    public String deleteHospitalDepartment(@PathVariable long id) {
        service.delete(id);
        return "redirect:/hospital-departments/";
    }
}
