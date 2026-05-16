package ru.itis.controlworkstub.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex,
                                       HttpServletRequest request,
                                       Model model) {
        log.warn("Entity not found at {}: {}", request.getRequestURI(), ex.getMessage());

        fillModel(model, 404, "Объект не найден");
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex,
                                 HttpServletRequest request,
                                 Model model) {
        log.warn("Page not found: {}", request.getRequestURI());

        fillModel(model, 404, "Страница не найдена");
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex,
                                     HttpServletRequest request,
                                     Model model) {
        log.warn("Access denied at {}: {}", request.getRequestURI(), ex.getMessage());

        fillModel(model, 403, "Доступ запрещён");
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex,
                                   HttpServletRequest request,
                                   Model model) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation error at {}: {}", request.getRequestURI(), message);

        fillModel(model, 400, message);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,
                                  HttpServletRequest request,
                                  Model model) {
        log.error("Unhandled error at {}", request.getRequestURI(), ex);

        fillModel(model, 500, "Что-то пошло не так...");
        return "error";
    }

    private void fillModel(Model model, int statusCode, String errorMessage) {
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("timestamp", LocalDateTime.now());
    }
}