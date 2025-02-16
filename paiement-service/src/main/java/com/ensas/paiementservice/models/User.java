package com.ensas.paiementservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private boolean enabled;
    private Date created;
}
