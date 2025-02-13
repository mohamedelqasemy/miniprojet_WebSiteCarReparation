package com.ensas.commandservice.web;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.services.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commands")
@AllArgsConstructor()
public class CommandRestController {
    private final CommandService commandService;

    //get all commands
    @GetMapping
    public ResponseEntity<List<CommandDto>> getAllCommands() {
        List<CommandDto> commandDtoList = commandService.getAllCommands();
        return ResponseEntity.ok(commandDtoList);
    }
}
