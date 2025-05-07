package com.ensas.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String num;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private Boolean enabled;
    private String image;
    private Date created;
    private String address;
}
