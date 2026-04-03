package edu.eci.dosw.techcup.controller;

import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService){
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(tournamentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Tournament tournament = tournamentService.getTournament(id);
        return (tournament == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(tournament);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Tournament tournament){
        boolean response = tournamentService.createTournament(tournament);
        return response ? ResponseEntity.status(HttpStatus.CREATED).body(tournament) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Tournament tournament){
        boolean response = tournamentService.updateTournament(id, tournament);
        return response ? ResponseEntity.ok(tournament) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean response = tournamentService.deleteTournament(id);
        return response ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
