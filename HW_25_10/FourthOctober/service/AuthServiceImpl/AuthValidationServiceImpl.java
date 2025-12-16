package org.example.FourthOctober.service.AuthServiceImpl;

import org.example.FourthOctober.DTO.FieldErrorDto;
import org.example.FourthOctober.service.AuthValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthValidationServiceImpl implements AuthValidationService {

    @Override
    public List<FieldErrorDto> validateEmail(String email) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(email)) {
            errors.add(new FieldErrorDto("email", "email is null"));
        } else {
            if (email.length() < 5) {
                errors.add(new FieldErrorDto("email", "email length should be 5"));
            }
            if (!email.matches("\\S+@\\S+\\.\\S+")) {
                errors.add(new FieldErrorDto("email", "invalid email format"));
            }
        }
        return errors;
    }

    @Override
    public List<FieldErrorDto> validatePassword(String password) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (password == null || password.trim().isEmpty()) {
            errors.add(new FieldErrorDto("password", "password length should be 5"));
            return errors;
        }
        if (password.length() < 5) {
            errors.add(new FieldErrorDto("password", "password is null"));
        }
        if (!password.matches("(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).*")) {
            errors.add(new FieldErrorDto("password", "invalid password format"));
        }

        return errors;

    }


    @Override
    public List<FieldErrorDto> validateUserName(String username) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(username)) {
            errors.add(new FieldErrorDto("username", "username is null"));
        }
        return errors;
    }


}
