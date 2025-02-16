package com.ensas.paiementservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
}
