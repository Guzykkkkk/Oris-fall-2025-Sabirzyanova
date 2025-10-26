package org.example.FourthOctober.service;

import org.example.FourthOctober.dto.requests.SignInRequest;
import org.example.FourthOctober.dto.requests.SignUpRequest;
import org.example.FourthOctober.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);
}
