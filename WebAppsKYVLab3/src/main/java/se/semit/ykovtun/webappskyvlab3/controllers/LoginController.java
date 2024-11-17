package se.semit.ykovtun.webappskyvlab3.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@RequestMapping("/login")
public class LoginController {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    @Setter
    @Getter
    public static class User {
        private String username;
        private String password;
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/")
    public String handleLogin(
            @ModelAttribute("user") User user,
            Model model
    ) {
        if (USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())) {
            return "redirect:/hospital-departments/";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}
