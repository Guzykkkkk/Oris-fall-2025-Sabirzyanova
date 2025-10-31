package org.example.FourthOctober.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDate date;
}
