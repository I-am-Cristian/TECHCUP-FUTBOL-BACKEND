package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    List<Tournament> findByName(String name);
}
