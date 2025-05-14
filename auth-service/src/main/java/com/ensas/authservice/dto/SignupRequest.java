package com.ensas.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String num;
    private String address;
    private String role;
}