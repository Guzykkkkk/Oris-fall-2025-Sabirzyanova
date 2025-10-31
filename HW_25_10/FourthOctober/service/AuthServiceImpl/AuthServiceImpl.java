package org.example.FourthOctober.service.AuthServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.FourthOctober.DAO.UserRepository;
import org.example.FourthOctober.DTO.FieldErrorDto;
import org.example.FourthOctober.DTO.Request.SignInRequest;
import org.example.FourthOctober.DTO.Request.SignUpRequest;
import org.example.FourthOctober.DTO.Response.AuthResponse;
import org.example.FourthOctober.model.User;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.AuthValidationService;
import org.example.FourthOctober.service.PasswordEncoder;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();

    
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            errors.add(new FieldErrorDto("email", "Email is required"));
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            errors.add(new FieldErrorDto("password", "Password is required"));
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            errors.add(new FieldErrorDto("name", "Name is required"));
        }

    
        if (!errors.isEmpty()) {
            return fail(errors);
        }

    
        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validatePassword(request.getPassword()));
        errors.addAll(validationService.validateUserName(request.getName()));

        if (!errors.isEmpty()) return fail(errors);

    
        LocalDate date = null;
        if (request.getDate() != null && !request.getDate().trim().isEmpty()) {
            try {
                date = LocalDate.parse(request.getDate());
            } catch (Exception e) {
                errors.add(new FieldErrorDto("date", "Invalid date format. Use YYYY-MM-DD"));
                return fail(errors);
            }
        }

    
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            errors.add(new FieldErrorDto("email", "User with this email already exists"));
            return fail(errors);
        }

    
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .date(date)    
                .build();

        userRepository.save(user);

        return ok(User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build());
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();

    
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            errors.add(new FieldErrorDto("email", "Email is required"));
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            errors.add(new FieldErrorDto("password", "Password is required"));
        }

        if (!errors.isEmpty()) return fail(errors);

        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validatePassword(request.getPassword()));

        if (!errors.isEmpty()) return fail(errors);

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            errors.add(new FieldErrorDto("email", "User not found"));
            return fail(errors);
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            errors.add(new FieldErrorDto("password", "Invalid password"));
            return fail(errors);
        }

        return ok(user);
    }

    private AuthResponse fail(List<FieldErrorDto> errors) {
        return AuthResponse.builder()
                .success(false)
                .errors(errors)
                .user(null)
                .build();
    }

    private AuthResponse ok(User user) {
        return AuthResponse.builder()
                .success(true)
                .user(user)
                .build();
    }
}