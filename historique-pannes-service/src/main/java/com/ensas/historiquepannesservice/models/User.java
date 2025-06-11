package com.ensas.historiquepannesservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class User {
    private Long id;
    private String email;
    private String num;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private Boolean enabled;
    private String image;
    private String publicId;
    private Date created;
    private String address;
}
