package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.TournamentDTO;
import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import edu.eci.dosw.techcup.mapper.TournamentMapper;
import edu.eci.dosw.techcup.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;

    public TournamentService(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    public boolean createTournament(Tournament tournament) {
        log.info("Creando torneo: {}", tournament.getName());
        try {
            tournamentRepository.save(tournament);
            log.info("Torneo creado exitosamente con id: {}", tournament.getId());
            return true;
        } catch (Exception e) {
            log.error("Error al crear torneo: {}", e.getMessage());
            return false;
        }
    }

    public boolean updateTournament(Long tournamentId, Tournament newTournament) {
        log.info("Actualizando torneo id: {}", tournamentId);
        
        Tournament existingTournament = tournamentRepository.findById(tournamentId).orElse(null);
        if (existingTournament == null) {
            log.warn("Torneo no encontrado con id: {}", tournamentId);
            return false;
        }
        
        if (existingTournament.getState() == TournamentState.ENDED) {
            log.warn("No se puede actualizar un torneo finalizado");
            return false;
        }
        
        newTournament.setId(tournamentId);
        tournamentRepository.save(newTournament);
        log.info("Torneo actualizado con id: {}", tournamentId);
        return true;
    }

    public boolean deleteTournament(Long tournamentId) {
        log.info("Eliminando torneo id: {}", tournamentId);
        
        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
        if (tournament == null) {
            log.warn("Torneo no encontrado con id: {}", tournamentId);
            return false;
        }
        
        if (tournament.getState() != TournamentState.DRAFT) {
            log.warn("Solo se pueden eliminar torneos en estado DRAFT");
            return false;
        }
        
        tournamentRepository.deleteById(tournamentId);
        log.info("Torneo eliminado con id: {}", tournamentId);
        return true;
    }

    public Tournament getTournament(Long tournamentId) {
        log.info("Buscando torneo id: {}", tournamentId);
        return tournamentRepository.findById(tournamentId).orElse(null);
    }

    public List<TournamentDTO> findAll() {
        log.info("Obteniendo todos los torneos");
        return tournamentRepository.findAll()
                .stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }
}