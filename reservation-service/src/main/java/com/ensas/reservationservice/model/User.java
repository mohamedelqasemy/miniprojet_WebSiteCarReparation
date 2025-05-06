package com.ensas.reservationservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
}
