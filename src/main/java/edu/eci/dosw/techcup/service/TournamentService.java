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

    public TournamentService(TournamentRepository tournamentRepository,
                             TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    public TournamentDTO createTournament(TournamentDTO dto) {
        log.info("Creando torneo: {}", dto.getName());
        Tournament tournament = tournamentMapper.toEntity(dto);
        Tournament saved = tournamentRepository.save(tournament);
        log.info("Torneo creado con id: {}", saved.getId());
        return tournamentMapper.toDto(saved);
    }

    public TournamentDTO updateTournament(Long id, TournamentDTO dto) {
        log.info("Actualizando torneo id: {}", id);
        Tournament existing = tournamentRepository.findById(id).orElse(null);
        if (existing == null) {
            log.warn("Torneo no encontrado con id: {}", id);
            return null;
        }
        if (existing.getState() == TournamentState.ENDED) {
            log.warn("No se puede actualizar un torneo finalizado");
            return null;
        }
        Tournament updated = tournamentMapper.toEntity(dto);
        updated.setId(id);
        Tournament saved = tournamentRepository.save(updated);
        log.info("Torneo actualizado con id: {}", id);
        return tournamentMapper.toDto(saved);
    }

    public boolean deleteTournament(Long id) {
        log.info("Eliminando torneo id: {}", id);
        Tournament tournament = tournamentRepository.findById(id).orElse(null);
        if (tournament == null) {
            log.warn("Torneo no encontrado con id: {}", id);
            return false;
        }
        if (tournament.getState() != TournamentState.DRAFT) {
            log.warn("Solo se pueden eliminar torneos en estado DRAFT");
            return false;
        }
        tournamentRepository.deleteById(id);
        log.info("Torneo eliminado con id: {}", id);
        return true;
    }

    public TournamentDTO getTournament(Long id) {
        log.info("Buscando torneo id: {}", id);
        return tournamentRepository.findById(id)
                .map(tournamentMapper::toDto)
                .orElse(null);
    }

    public List<TournamentDTO> findAll() {
        log.info("Obteniendo todos los torneos");
        return tournamentRepository.findAll()
                .stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }
}