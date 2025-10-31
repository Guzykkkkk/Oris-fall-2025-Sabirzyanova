package org.example.FourthOctober.service;

import org.example.FourthOctober.DTO.Request.SignInRequest;
import org.example.FourthOctober.DTO.Request.SignUpRequest;
import org.example.FourthOctober.DTO.Response.AuthResponse;

public interface AuthService {
        AuthResponse signUp(SignUpRequest request);

        AuthResponse signIn(SignInRequest request);

}
