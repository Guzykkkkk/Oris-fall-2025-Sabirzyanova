package org.example.FourthOctober.service;


import org.example.FourthOctober.DTO.FieldErrorDto;

import java.util.List;

public interface AuthValidationService {
    List<FieldErrorDto> validateEmail(String email);
    List<FieldErrorDto> validatePassword(String password);
    List<FieldErrorDto> validateUserName(String username);

}
