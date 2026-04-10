package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.PlayerDTO;
import edu.eci.dosw.techcup.entity.Player;
import edu.eci.dosw.techcup.mapper.PlayerMapper;
import edu.eci.dosw.techcup.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        logger.info("Creando jugador: {}", playerDTO.getName());
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDto(savedPlayer);
    }

    public PlayerDTO getPlayer(Long id) {
        logger.info("Buscando jugador id: {}", id);
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
        return playerMapper.toDto(player);
    }

    public List<PlayerDTO> getAllPlayers() {
        logger.info("Listando todos los jugadores");
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO) {
        logger.info("Actualizando jugador id: {}", id);
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
        
        existingPlayer.setName(playerDTO.getName());
        existingPlayer.setDorsal(playerDTO.getDorsal());
        existingPlayer.setPosition(playerDTO.getPosition());
        existingPlayer.setParticipantType(playerDTO.getParticipantType());
        existingPlayer.setSemester(playerDTO.getSemester());
        existingPlayer.setAvailable(playerDTO.isAvailable());
        
        Player updatedPlayer = playerRepository.save(existingPlayer);
        return playerMapper.toDto(updatedPlayer);
    }

    public void deletePlayer(Long id) {
        logger.info("Eliminando jugador id: {}", id);
        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Jugador no encontrado con id: " + id);
        }
        playerRepository.deleteById(id);
    }
}