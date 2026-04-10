package edu.eci.dosw.techcup.controller;

import edu.eci.dosw.techcup.dto.PlayerDTO;
import edu.eci.dosw.techcup.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@Tag(name = "Jugadores", description = "Operaciones relacionadas con jugadores")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los jugadores")
    public ResponseEntity<List<PlayerDTO>> getAll() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener jugador por ID")
    public ResponseEntity<PlayerDTO> getById(@PathVariable Long id) {
        PlayerDTO player = playerService.getPlayer(id);
        return (player == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(player);
    }

    @PostMapping
    @Operation(summary = "Crear jugador")
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO created = playerService.createPlayer(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar jugador")
    public ResponseEntity<PlayerDTO> update(@PathVariable Long id,
                                            @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updated = playerService.updatePlayer(id, playerDTO);
        return (updated == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar jugador")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}