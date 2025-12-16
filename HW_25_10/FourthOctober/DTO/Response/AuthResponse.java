package org.example.FourthOctober.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.FourthOctober.DTO.FieldErrorDto;
import org.example.FourthOctober.model.User;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthResponse {
    private boolean success;

    private List<FieldErrorDto> errors;
    private User user;
}
