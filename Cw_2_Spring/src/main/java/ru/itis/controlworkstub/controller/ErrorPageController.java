package ru.itis.controlworkstub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class ErrorPageController {

    @GetMapping("/error/403")
    public String forbidden(Model model) {
        model.addAttribute("statusCode", 403);
        model.addAttribute("errorMessage", "Доступ запрещён");
        model.addAttribute("timestamp", LocalDateTime.now());

        return "error";
    }
}