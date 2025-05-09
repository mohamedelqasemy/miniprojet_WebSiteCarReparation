package com.ensas.equipementservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CommentResponseDto {
    private String text;
    private String userName;
    private String userImage;
    private Date date;
}
