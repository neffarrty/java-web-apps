package se.semit.ykovtun.webappskyvlab4.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.semit.ykovtun.webappskyvlab4.entities.User;
import se.semit.ykovtun.webappskyvlab4.services.user.UserService;

@Controller
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService service;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @PostMapping("/register")
    public String handleRegister(
        @ModelAttribute User user,
        BindingResult result, Model model
    ) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (result.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
            model.addAttribute("user", user);
            return "auth/registration";
        }

        try {
            service.registerUser(user);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/registration";
        }

        model.addAttribute("username", username);
        model.addAttribute("password", password);

        return "auth/login";
    }
}
