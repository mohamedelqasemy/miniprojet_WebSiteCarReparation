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
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private boolean enabled;
    private Date created;
}
