package edu.eci.dosw.techcup.controller;

import edu.eci.dosw.techcup.dto.TournamentDTO;
import edu.eci.dosw.techcup.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@Tag(name = "Torneos", description = "Operaciones relacionadas con torneos")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los torneos")
    public ResponseEntity<List<TournamentDTO>> findAll() {
        return ResponseEntity.ok(tournamentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener torneo por ID")
    public ResponseEntity<TournamentDTO> findById(@PathVariable Long id) {
        TournamentDTO dto = tournamentService.getTournament(id);
        return (dto == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear torneo")
    public ResponseEntity<TournamentDTO> save(@RequestBody TournamentDTO tournamentDTO) {
        TournamentDTO created = tournamentService.createTournament(tournamentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar torneo")
    public ResponseEntity<TournamentDTO> update(@PathVariable Long id,
                                                @RequestBody TournamentDTO tournamentDTO) {
        TournamentDTO updated = tournamentService.updateTournament(id, tournamentDTO);
        return (updated == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar torneo en estado DRAFT")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = tournamentService.deleteTournament(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}