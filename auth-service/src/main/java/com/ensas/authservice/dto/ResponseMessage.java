package com.ensas.authservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ResponseMessage {
    private String message;
    private boolean success;
}
