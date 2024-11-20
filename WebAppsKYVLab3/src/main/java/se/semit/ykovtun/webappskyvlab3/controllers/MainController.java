package se.semit.ykovtun.webappskyvlab3.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Controller
@RequestMapping("/")
public class MainController {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    @Setter
    @Getter
    public static class User {
        private String username;
        private String password;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
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
