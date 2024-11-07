package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.services.HospitalDepartmentService;

import java.util.List;

@Controller
@RequestMapping(path = "/hospital-departments")
@AllArgsConstructor
public class HospitalDepartmentController {
    private final HospitalDepartmentService service;

    @GetMapping("/")
    public String listHospitalDepartments(Model model) {
        List<HospitalDepartment> departments = service.findAll();
        model.addAttribute("departments", departments);
        return "hospital-department/hospital-departments";
    }

    @GetMapping("/{id}")
    public String getHospitalDepartment(@PathVariable long id, Model model) {
        HospitalDepartment department = service.findById(id);
        model.addAttribute("department", department);
        return "hospital-department/hospital-department";
    }

    @GetMapping("/new")
    public String createHospitalDepartmentForm(Model model) {
        HospitalDepartment department = new HospitalDepartment();
        model.addAttribute("department", department);
        return "hospital-department/hospital-department-new";
    }

    @GetMapping("/edit/{id}")
    public String editHospitalDepartmentForm(@PathVariable long id, Model model) {
        HospitalDepartment department = service.findById(id);
        model.addAttribute("department", department);
        return "hospital-department/hospital-department-edit";
    }

    @PostMapping("/")
    public String createHospitalDepartment(
        @Valid @ModelAttribute HospitalDepartment department,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.department", result);
            model.addAttribute("department", department);
            return "hospital-department/hospital-department-new";
        }
        this.service.create(department);
        return "redirect:/hospital-departments/";
    }

    @PatchMapping("/{id}")
    public String updateHospitalDepartment(
        @PathVariable long id,
        @Valid @ModelAttribute HospitalDepartment department,
        BindingResult result, Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.department", result);
            model.addAttribute("department", department);
            return "hospital-department/hospital-department-edit";
        }
        this.service.update(id, department);
        return "redirect:/hospital-departments/";
    }

    @DeleteMapping("/{id}")
    public String deleteHospitalDepartment(@PathVariable long id) {
        this.service.delete(id);
        return "redirect:/hospital-departments/";
    }
}
