package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}