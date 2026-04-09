package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    List<Player> findByName(String name);
}
