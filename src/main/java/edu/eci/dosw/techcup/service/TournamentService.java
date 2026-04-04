package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TournamentService {
    private Map<Long, Tournament> tournaments;
    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    public TournamentService() {
        this.tournaments = new LinkedHashMap<Long, Tournament>();
    }

    public boolean createTournament(Tournament tournament) {
        if(tournament == null) {
            log.warn("No se puede crear un torneo nulo");
            return false;
        }
        log.info("Creando un torneo: {}", tournament.getName());
        if(!tournaments.containsKey(tournament.getId())) {
            tournaments.put(tournament.getId(), tournament);
            log.info("Torneo creado exitosamente con id: {}", tournament.getId());
            return true;
        }
        log.warn("Fue imposible crear el torneo, id duplicado");
        return false;
    }

    public boolean updateTournament(long tournamentId, Tournament newTournament) {
        log.info("Actualizando torneo: {}", newTournament.getName());
        if((tournamentId == newTournament.getId()) && tournaments.containsKey(tournamentId) && (tournaments.get(tournamentId).getState() != TournamentState.ENDED)) {
            tournaments.put(tournamentId, newTournament);
            log.info("Torneo actualizado con id: {}", tournamentId);
            return true;
        }
        log.warn("Fue imposible actualizar el torneo");
        return false;
    }

    public boolean deleteTournament(long tournamentId) {
        log.info("Eliminando torneo: {}", tournamentId);
        if(tournaments.containsKey(tournamentId) && (tournaments.get(tournamentId).getState() == TournamentState.DRAFT)) {
            tournaments.remove(tournamentId);
            log.info("Torneo eliminado con id: {}", tournamentId);
            return true;
        }
        log.warn("Fue imposible eliminar el torneo");
        return false;
    }

    public Tournament getTournament(long tournamentId) {
        log.info("Retornando torneo: {}", tournamentId);
        return tournaments.get(tournamentId);
    }

    public Map<Long, Tournament> findAll(){
        log.info("Obteniendo todos los torneos");
        return tournaments;
    }
}
