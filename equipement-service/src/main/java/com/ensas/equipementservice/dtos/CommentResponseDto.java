package com.ensas.equipementservice.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CommentResponseDto {
    private String text;
    private String userName;
    private String userImage;
    private Date date;
}
