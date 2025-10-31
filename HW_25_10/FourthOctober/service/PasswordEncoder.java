package org.example.FourthOctober.service;

public interface PasswordEncoder {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String hashPassword);
}
