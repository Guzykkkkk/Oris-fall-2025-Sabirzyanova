package org.example.FourthOctober.service.Impl;


import lombok.RequiredArgsConstructor;
import org.example.FourthOctober.dto.FieldErrorDto;
import org.example.FourthOctober.dto.requests.SignInRequest;
import org.example.FourthOctober.dto.requests.SignUpRequest;
import org.example.FourthOctober.dto.response.AuthResponse;
import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.AuthDataValidationService;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthDataValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validatePassword(request.getPassword()));
        errors.addAll(validationService.validateNickname(request.getNickname()));

        if(!errors.isEmpty()) return fail(errors);

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            errors.add(new FieldErrorDto("email", "Email taken"));
            return fail(errors);
        }

        userRepository.save(UserEntity.builder()
                .email(request.getEmail())
                .username(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
        return ok();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validatePassword(request.getPassword()));

        if(!errors.isEmpty()) return fail(errors);

        Optional<UserEntity> optionalUser = userRepository.findByEmail(request.getEmail());
        if(optionalUser.isEmpty()) {
            errors.add(new FieldErrorDto("email", "Email not found"));
            return fail(errors);
        }
        if(!passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            errors.add(new FieldErrorDto("password", "Password incorrect"));
            return fail(errors);
        }
        return ok();
    }

    private AuthResponse fail(List<FieldErrorDto> errors) {
        return AuthResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private AuthResponse ok() {
        return AuthResponse.builder()
                .success(true)
                .build();
    }
}
