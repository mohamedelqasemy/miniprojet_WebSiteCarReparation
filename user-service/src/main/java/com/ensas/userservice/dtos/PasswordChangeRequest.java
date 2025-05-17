package com.ensas.userservice.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {
    private String currentPassword;
    private String newPassword;
}
