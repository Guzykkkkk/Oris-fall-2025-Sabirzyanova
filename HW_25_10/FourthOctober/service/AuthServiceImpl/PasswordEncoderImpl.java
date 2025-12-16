package org.example.FourthOctober.service.AuthServiceImpl;

import org.example.FourthOctober.service.PasswordEncoder;

import java.util.Objects;

public class PasswordEncoderImpl  implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        return "HASH" + Objects.hashCode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String hashPassword) {
        return encode(rawPassword).equals(hashPassword);
    }
}
