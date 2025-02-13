package com.ensas.commandservice.entities;

import com.ensas.commandservice.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private int total;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    private Long userId;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL)
    private List<CommandDetails> commandDetails;
}
