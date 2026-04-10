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

    public TournamentDTO createTournament(TournamentDTO tournamentDTO) {
        log.info("Creando torneo: {}", tournamentDTO.getName());
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        Tournament savedTournament = tournamentRepository.save(tournament);
        log.info("Torneo creado exitosamente con id: {}", savedTournament.getId());
        return tournamentMapper.toDto(savedTournament);
    }

    public TournamentDTO updateTournament(Long tournamentId, TournamentDTO tournamentDTO) {
        log.info("Actualizando torneo id: {}", tournamentId);
        
        Tournament existingTournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + tournamentId));
        
        if (existingTournament.getState() == TournamentState.ENDED) {
            throw new RuntimeException("No se puede actualizar un torneo finalizado");
        }
        
        existingTournament.setName(tournamentDTO.getName());
        existingTournament.setInitialDate(tournamentDTO.getInitialDate());
        existingTournament.setFinalDate(tournamentDTO.getFinalDate());
        existingTournament.setState(tournamentDTO.getState());
        existingTournament.setInscriptionCost(tournamentDTO.getInscriptionCost());
        existingTournament.setTeamsNumber(tournamentDTO.getTeamsNumber());
        
        Tournament updatedTournament = tournamentRepository.save(existingTournament);
        log.info("Torneo actualizado con id: {}", tournamentId);
        return tournamentMapper.toDto(updatedTournament);
    }

    public void deleteTournament(Long tournamentId) {
        log.info("Eliminando torneo id: {}", tournamentId);
        
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + tournamentId));
        
        // Solo eliminar si está en DRAFT
        if (tournament.getState() != TournamentState.DRAFT) {
            throw new RuntimeException("Solo se pueden eliminar torneos en estado DRAFT");
        }
        
        tournamentRepository.deleteById(tournamentId);
        log.info("Torneo eliminado con id: {}", tournamentId);
    }

    public TournamentDTO getTournament(Long tournamentId) {
        log.info("Buscando torneo id: {}", tournamentId);
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + tournamentId));
        return tournamentMapper.toDto(tournament);
    }

    public List<TournamentDTO> findAll() {
        log.info("Obteniendo todos los torneos");
        return tournamentRepository.findAll()
                .stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TournamentDTO> findByNameContaining(String name) {
        log.info("Buscando torneos por nombre: {}", name);
        return tournamentRepository.findByNombreContainingIgnoreCase(name)
                .stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }
}