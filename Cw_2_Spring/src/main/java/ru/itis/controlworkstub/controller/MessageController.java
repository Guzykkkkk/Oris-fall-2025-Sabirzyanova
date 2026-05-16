package ru.itis.controlworkstub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.controlworkstub.model.MessageEntity;
import ru.itis.controlworkstub.service.MessageForm;
import ru.itis.controlworkstub.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class MessageController {

    private final MessageService messageService;

    // Лента диалогов (последнее сообщение в каждом диалоге)
    @GetMapping
    public String feed(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("dialogs", messageService.getDialogs());
        model.addAttribute("currentUsername", currentUsername);
        return "feed";
    }

    // Страница конкретного диалога с пользователем
    @GetMapping("/{userId}")
    public String dialog(@PathVariable Long userId, Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("messages", messageService.getConversationWith(userId));
        model.addAttribute("recipientId", userId);
        model.addAttribute("currentUsername", currentUsername);
        model.addAttribute("messageForm", new MessageForm());

        return "dialog";
    }

    // Отправка сообщения из формы на странице диалога
    @PostMapping("/{userId}")
    public String sendMessage(@PathVariable Long userId,
                              @Valid @ModelAttribute("messageForm") MessageForm messageForm,
                              BindingResult bindingResult,
                              Model model) {

        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            model.addAttribute("messages", messageService.getConversationWith(userId));
            model.addAttribute("recipientId", userId);
            model.addAttribute("currentUsername", currentUsername);

            return "dialog";
        }

        messageService.sendMessage(userId, messageForm.getText());

        return "redirect:/feed/" + userId;
    }

}
