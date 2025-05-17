package com.ensas.commandservice.web;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.services.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands")
@AllArgsConstructor
public class CommandRestController {
    private final CommandService commandService;

    // Créer une commande
    @PostMapping
    public ResponseEntity<CommandDto> createCommand(@RequestBody CommandDto commandDto) {
        CommandDto createdCommand = commandService.createCommand(commandDto);
        return ResponseEntity.status(HttpStatus.CREATED)  // Retourne un statut 201 Created
                .header("Location", "/commands/" + createdCommand.getId())
                .body(createdCommand);
    }

    // Obtenir toutes les commandes avec pagination
    @GetMapping
    public ResponseEntity<Page<CommandDto>> getAllCommands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CommandDto> commandDtoPage = commandService.getAllCommands(page, size);
        return ResponseEntity.ok(commandDtoPage);
    }

    // Obtenir une commande spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<CommandDto> getCommand(@PathVariable("id") Long id) {
        CommandDto commandDto = commandService.getCommandById(id);
        return ResponseEntity.ok(commandDto);
    }

    // Mettre à jour une commande
    @PutMapping("/{id}")
    public ResponseEntity<CommandDto> updateCommand(@PathVariable("id") Long id, @RequestBody CommandDto commandDto) {
        CommandDto updatedCommand = commandService.updateCommand(id, commandDto);
        return ResponseEntity.ok(updatedCommand);
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable("id") Long id) {
        commandService.deleteCommand(id);
        return ResponseEntity.noContent().build(); // Retourne un statut 204 No Content
    }

    // Obtenir les commandes d'un utilisateur par ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommandDto>> getCommandsByUserId(@PathVariable("userId") Long userId) {
        List<CommandDto> commands = commandService.getCommandsByUserId(userId);
        return ResponseEntity.ok(commands);
    }

    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Page<CommandDto>> getCommandsByUserIdPaginated(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<CommandDto> commands = commandService.getCommandsByUserIdPaginated(userId, page, size);
        return ResponseEntity.ok(commands);
    }
}
