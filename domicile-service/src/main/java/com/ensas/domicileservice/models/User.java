package com.ensas.domicileservice.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String role;
}
