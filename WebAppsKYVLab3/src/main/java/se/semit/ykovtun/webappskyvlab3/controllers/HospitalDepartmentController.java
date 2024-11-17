package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.services.department.HospitalDepartmentService;
import se.semit.ykovtun.webappskyvlab3.services.patient.PatientServiceImpl;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Controller
@RequestMapping(path = "/hospital-departments")
@AllArgsConstructor
public class HospitalDepartmentController {
    private final PatientServiceImpl patientService;
    private final HospitalDepartmentService departmentService;

    @GetMapping("/")
    public String listHospitalDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "hospital-department/list";
    }

    @GetMapping("/{id}/patients")
    public String listPatients(@PathVariable long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        return "hospital-department/patients";
    }

    @GetMapping("/{id}/patients/new")
    public String newPatientForm(@PathVariable long id, Model model) {
        HospitalDepartment department = departmentService.findById(id);
        Patient patient = new Patient();
        patient.setDepartment(department);
        model.addAttribute("department", department);
        model.addAttribute("patient", patient);
        return "hospital-department/new-patient";
    }

    @GetMapping("/{id}/patients/{patientId}/edit")
    public String editPatientForm(
        @PathVariable long id,
        @PathVariable long patientId,
        Model model
    ) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute("patient", patientService.findById(patientId));
        return "hospital-department/edit-patient";
    }

    @GetMapping("/new")
    public String newHospitalDepartmentForm(Model model) {
        model.addAttribute("department", new HospitalDepartment());
        return "hospital-department/new";
    }

    @GetMapping("/{id}/edit")
    public String editHospitalDepartmentForm(@PathVariable long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
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
            departmentService.create(department);
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
            model.addAttribute("department", departmentService.findById(id));
            model.addAttribute("patient", patient);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            return "hospital-department/new-patient";
        }
        departmentService.addPatient(id, patient);
        return "redirect:/hospital-departments/" + id + "/patients";
    }

    @PatchMapping("/{id}/patients/{patientId}")
    public String editPatient(
        @PathVariable long id,
        @PathVariable long patientId,
        @Valid @ModelAttribute Patient patient,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.findById(id));
            model.addAttribute("patient", patient);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "patient", result);
            return "hospital-department/edit-patient";
        }
        patientService.update(patientId, patient);
        System.out.println("Hello");
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
            departmentService.update(id, department);
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
        departmentService.delete(id);
        return "redirect:/hospital-departments/";
    }
}
