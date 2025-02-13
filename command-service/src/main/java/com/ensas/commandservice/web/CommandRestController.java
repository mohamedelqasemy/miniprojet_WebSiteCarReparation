package com.ensas.commandservice.web;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.services.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands")
@AllArgsConstructor()
public class CommandRestController {
    private final CommandService commandService;

    //creer une command
    @PostMapping
    public ResponseEntity<CommandDto> createCommand(@RequestBody CommandDto commandDto) {
        CommandDto commanddto = commandService.createCommand(commandDto);
        return ResponseEntity.ok(commanddto);
    }

    //get all commands
    @GetMapping
    public ResponseEntity<List<CommandDto>> getAllCommands() {
        List<CommandDto> commandDtoList = commandService.getAllCommands();
        return ResponseEntity.ok(commandDtoList);
    }

    //get all commands
    @GetMapping("/{id}")
    public ResponseEntity<CommandDto> getCommand(@PathVariable("id") Long id) {
        CommandDto commandDto = commandService.getCommandById(id);
        return ResponseEntity.ok(commandDto);
    }

    //update a command
    @PutMapping("/{id}")
    public ResponseEntity<CommandDto> updateCommand(@PathVariable("id") Long id, @RequestBody CommandDto commandDto) {
        CommandDto updatedCommand = commandService.updateCommand(id, commandDto);
        return ResponseEntity.ok(updatedCommand);
    }

    //delete a command
    @DeleteMapping("/{id}")
    public ResponseEntity<CommandDto> deleteCommand(@PathVariable("id") Long id) {
        commandService.deleteCommand(id);
        return ResponseEntity.noContent().build();
    }



}
