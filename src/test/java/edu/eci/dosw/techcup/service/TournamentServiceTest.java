package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentServiceTest {

    private TournamentService service;

    @BeforeEach
    public void setUp() {
        service = new TournamentService();
    }

    @Test
    public void shouldCreateTournament() {
        //Having
        Tournament newTournament = new Tournament(101001001, "miPrimerTorneo");

        //When
        boolean response = service.createTournament(newTournament);

        //Then
        assertTrue(response);
        assertEquals(TournamentState.DRAFT, service.getTournament(101001001).getState());
    }

    @Test
    public void shouldNotCreateTournament() {
        //Having
        Tournament newTournament = null;

        //When
        boolean response = service.createTournament(newTournament);

        //Then
        assertFalse(response);
    }

    @Test
    public void shouldNotCreateTournamentIdRepeated() {
        //Having
        Tournament firstTournament = new Tournament(77777777, "Torneo Piola");
        service.createTournament(firstTournament);
        Tournament newTournament = new Tournament(77777777, "Torneo copia id");

        //When
        boolean response = service.createTournament(newTournament);

        //Then
        assertFalse(response);

    }

    @Test
    public void shouldUpdateTournament() {
        //Having
        Tournament tournament = new Tournament(101001001, "miPrimerTorneo");
        service.createTournament(tournament);
        Tournament updatedTournament = new Tournament(101001001, "Torneo actualizado");
        updatedTournament.setInitialDate(LocalDate.now());
        updatedTournament.setFinalDate(LocalDate.now());

        //When
        boolean response = service.updateTournament(updatedTournament.getId(), updatedTournament);

        //Then
        assertTrue(response);
        assertEquals("Torneo actualizado", service.getTournament(101001001).getName());
        assertEquals(TournamentState.DRAFT, service.getTournament(101001001).getState());
    }

    @Test
    public void shouldNotUpdateTournamentNonExistentId() {
        //Having
        Tournament tournament = new Tournament(101001001, "Torneo1");
        Tournament tournament2 = new Tournament(101001002, "Torneo2");
        tournament2.setState(TournamentState.ACTIVE);
        service.createTournament(tournament);
        service.createTournament(tournament2);
        Tournament notRegisteredTournament = new Tournament(123123, "Torneo sin registro");

        //When
        boolean response = service.updateTournament(notRegisteredTournament.getId(), notRegisteredTournament);

        //Then
        assertFalse(response);
    }

    @Test
    public void shouldNotUpdateTournamentInvalidState() {
        //Having
        Tournament tournament = new Tournament(101001001, "Torneo finalizado");
        tournament.setState(TournamentState.ENDED);
        service.createTournament(tournament);
        Tournament updatedTournament = new Tournament(101001001, "No me pueden actualizar");

        //When
        boolean response = service.updateTournament(updatedTournament.getId(), updatedTournament);

        //Then
        assertFalse(response);
    }

    @Test
    public void shouldDeleteTournament() {
        //Having
        Tournament tournament = new Tournament(101001001, "Borrame");
        service.createTournament(tournament);

        //When
        boolean response = service.deleteTournament(101001001);

        //Then
        assertTrue(response);
    }

    @Test
    public void shouldNotDeleteTournamentNonExistentId() {
        //Having
        Tournament tournament1 = new Tournament(101001001, "No me Borre");
        Tournament tournament2 = new Tournament(101001002, "No me va a borrar");
        service.createTournament(tournament1);
        service.createTournament(tournament2);

        //When
        boolean response = service.deleteTournament(321654);

        //Then
        assertFalse(response);
    }

    @Test
    public void shouldNotDeleteTournamentInvalidState() {
        //Having
        Tournament tournament1 = new Tournament(101001001, "J");
        Tournament tournament2 = new Tournament(101001002, "A");
        Tournament tournament3 = new Tournament(101001003, "J");
        Tournament tournament4 = new Tournament(101001004, "A");
        tournament1.setState(TournamentState.ACTIVE);
        tournament2.setState(TournamentState.IN_PROGRESS);
        tournament3.setState(TournamentState.ACTIVE);
        tournament4.setState(TournamentState.ENDED);
        service.createTournament(tournament1);
        service.createTournament(tournament2);
        service.createTournament(tournament3);
        service.createTournament(tournament4);

        //When
        boolean response1 = service.deleteTournament(101001001);
        boolean response2 = service.deleteTournament(101001002);
        boolean response3 = service.deleteTournament(101001003);
        boolean response4 = service.deleteTournament(101001004);

        //Then
        assertFalse(response1 || response2 || response3 || response4);
    }
}