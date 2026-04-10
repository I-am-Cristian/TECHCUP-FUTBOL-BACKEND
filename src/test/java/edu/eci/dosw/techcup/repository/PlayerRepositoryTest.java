package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Debe guardar un jugador correctamente")
    void shouldSavePlayer() {
        Player player = new Player(null, "player@gmail.com", "123456",
                "Carlos", 10, Position.STRIKER, ParticipantType.STUDENT);
        player.setRole(UserRole.JUGADOR);
        player.setState(MemberState.ACTIVE);
        player.setSemester(5);

        Player saved = playerRepository.save(player);

        assertNotNull(saved.getId());
        assertEquals("Carlos", saved.getName());
        assertEquals(10, saved.getDorsal());
    }

    @Test
    @DisplayName("Debe consultar todos los jugadores")
    void shouldFindAllPlayers() {
        Player p1 = new Player(null, "p1@gmail.com", "123456",
                "Juan", 1, Position.GOALKEEPER, ParticipantType.STUDENT);
        p1.setRole(UserRole.JUGADOR);
        p1.setState(MemberState.ACTIVE);

        Player p2 = new Player(null, "p2@gmail.com", "123456",
                "Pedro", 2, Position.DEFENDER, ParticipantType.STUDENT);
        p2.setRole(UserRole.JUGADOR);
        p2.setState(MemberState.ACTIVE);

        playerRepository.save(p1);
        playerRepository.save(p2);

        List<Player> players = playerRepository.findAll();
        assertEquals(2, players.size());
    }

    @Test
    @DisplayName("Debe eliminar un jugador correctamente")
    void shouldDeletePlayer() {
        Player player = new Player(null, "delete@gmail.com", "123456",
                "A eliminar", 99, Position.STRIKER, ParticipantType.STUDENT);
        player.setRole(UserRole.JUGADOR);
        player.setState(MemberState.ACTIVE);
        Player saved = playerRepository.save(player);

        playerRepository.deleteById(saved.getId());

        Optional<Player> deleted = playerRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Debe actualizar la disponibilidad de un jugador")
    void shouldUpdatePlayerAvailability() {
        Player player = new Player(null, "avail@gmail.com", "123456",
                "Disponible", 7, Position.MEDIUM, ParticipantType.STUDENT);
        player.setRole(UserRole.JUGADOR);
        player.setState(MemberState.ACTIVE);
        player.setAvailable(false);
        Player saved = playerRepository.save(player);

        saved.setAvailable(true);
        Player updated = playerRepository.save(saved);

        assertTrue(updated.isAvailable());
    }
}