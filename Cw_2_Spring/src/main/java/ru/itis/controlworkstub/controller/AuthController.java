package ru.itis.controlworkstub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.controlworkstub.model.UserEntity;
import ru.itis.controlworkstub.repository.UserRepository;
import ru.itis.controlworkstub.service.RegisterForm;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";   // шаблон login.ftlh
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register"; // шаблон register.ftlh
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form, BindingResult bindingResult, Model model) {
        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "username.exists", "username already exists");
        }
        if  (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "register";
        }
        UserEntity user = UserEntity.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.save(user);

        return "redirect:/login?registered";
    }


}
