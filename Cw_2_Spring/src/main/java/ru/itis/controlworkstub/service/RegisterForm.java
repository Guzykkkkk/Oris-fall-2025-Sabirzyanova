package ru.itis.controlworkstub.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "sorry, {validation.username.notBlank}")
    @Size(min = 3, max = 20, message = "sorry, {validation.username.size}")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я0-9_]+$",
            message = "sorru, {validation.username.pattern}"
    )
    private String username;

    @NotBlank(message = "sorry, {validation.password.notBlank}")
    @Size(min = 6, message = "sorry, {validation.password.size}")
    private String password;
}
