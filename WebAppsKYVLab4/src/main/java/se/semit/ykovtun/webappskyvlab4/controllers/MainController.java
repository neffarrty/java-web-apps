package se.semit.ykovtun.webappskyvlab4.controllers;

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
    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/login";
    }
}
