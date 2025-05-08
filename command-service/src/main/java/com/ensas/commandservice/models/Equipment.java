package com.ensas.commandservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Equipment {
    private Long id;
    private String name;
    private List<String> imageLinks;
}
