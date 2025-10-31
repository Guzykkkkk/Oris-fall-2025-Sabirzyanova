package org.example.FourthOctober.DTO.Request;

import lombok.*;

@Data
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String date;
}
