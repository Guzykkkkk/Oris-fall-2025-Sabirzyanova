package ru.itis.controlworkstub.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageForm {

    @NotBlank(message = "sorry, {validation.message.notBlank}")
    @Size(max = 2000, message = "sorry, {validation.message.size}")
    private String text;
}