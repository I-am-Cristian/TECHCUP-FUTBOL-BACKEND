package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TournamentService {
    private Map<Long, Tournament> tournaments;

    public TournamentService() {
        this.tournaments = new LinkedHashMap<Long, Tournament>();
    }

    public boolean createTournament(Tournament tournament) {
        if(tournament != null && !(tournaments.containsKey(tournament.getId()))) {
            tournaments.put(tournament.getId(), tournament);
            return true;
        }
        return false;
    }

    public boolean updateTournament(long tournamentId, Tournament newTournament) {
        if((tournamentId == newTournament.getId()) && tournaments.containsKey(tournamentId) && (tournaments.get(tournamentId).getState() != TournamentState.ENDED)) {
            tournaments.put(tournamentId, newTournament);
            return true;
        }
        return false;
    }

    public boolean deleteTournament(long tournamentId) {
        if(tournaments.containsKey(tournamentId) && (tournaments.get(tournamentId).getState() == TournamentState.DRAFT)) {
            tournaments.remove(tournamentId);
            return true;
        }
        return false;
    }

    public Tournament getTournament(long tournamentId) {
        return tournaments.get(tournamentId);
    }

    public Map<Long, Tournament> findAll(){
        return tournaments;
    }
}
