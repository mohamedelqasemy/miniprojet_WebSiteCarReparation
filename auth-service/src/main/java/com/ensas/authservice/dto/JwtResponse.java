package com.ensas.authservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String image;
    private String publicId;
    private String role;
    private Long id;

    public JwtResponse(String token) {
        this.token = token;
    }
}