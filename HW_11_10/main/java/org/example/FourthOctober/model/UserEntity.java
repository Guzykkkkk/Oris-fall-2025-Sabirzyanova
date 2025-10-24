package org.example.FourthOctober.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Long avatarFile;



}
