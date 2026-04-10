package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    @DisplayName("Debe guardar un torneo correctamente")
    void shouldSaveTournament() {
        Tournament tournament = new Tournament();
        tournament.setName("Copa TechCup");
        tournament.setState(TournamentState.DRAFT);
        tournament.setInitialDate(LocalDate.of(2025, 6, 1));
        tournament.setFinalDate(LocalDate.of(2025, 6, 30));
        tournament.setTeamsNumber(8);
        tournament.setInscriptionCost(50000);

        Tournament saved = tournamentRepository.save(tournament);

        assertNotNull(saved.getId());
        assertEquals("Copa TechCup", saved.getName());
        assertEquals(TournamentState.DRAFT, saved.getState());
    }

    @Test
    @DisplayName("Debe consultar todos los torneos")
    void shouldFindAllTournaments() {
        Tournament t1 = new Tournament();
        t1.setName("Torneo A");
        t1.setState(TournamentState.DRAFT);

        Tournament t2 = new Tournament();
        t2.setName("Torneo B");
        t2.setState(TournamentState.ACTIVE);

        tournamentRepository.save(t1);
        tournamentRepository.save(t2);

        List<Tournament> tournaments = tournamentRepository.findAll();

        assertEquals(2, tournaments.size());
    }

    @Test
    @DisplayName("Debe buscar torneo por nombre")
    void shouldFindByNameContaining() {
        Tournament tournament = new Tournament();
        tournament.setName("Copa Regional");
        tournament.setState(TournamentState.DRAFT);
        tournamentRepository.save(tournament);

        List<Tournament> results = tournamentRepository
                .findByNameContainingIgnoreCase("copa");

        assertEquals(1, results.size());
        assertEquals("Copa Regional", results.get(0).getName());
    }

    @Test
    @DisplayName("Debe eliminar un torneo correctamente")
    void shouldDeleteTournament() {
        Tournament tournament = new Tournament();
        tournament.setName("Torneo a eliminar");
        tournament.setState(TournamentState.DRAFT);
        Tournament saved = tournamentRepository.save(tournament);

        tournamentRepository.deleteById(saved.getId());

        Optional<Tournament> deleted = tournamentRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Debe actualizar el estado de un torneo")
    void shouldUpdateTournamentState() {
        Tournament tournament = new Tournament();
        tournament.setName("Torneo Update");
        tournament.setState(TournamentState.DRAFT);
        Tournament saved = tournamentRepository.save(tournament);

        saved.setState(TournamentState.ACTIVE);
        Tournament updated = tournamentRepository.save(saved);

        assertEquals(TournamentState.ACTIVE, updated.getState());
    }
}