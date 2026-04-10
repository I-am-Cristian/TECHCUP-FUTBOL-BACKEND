package edu.eci.dosw.techcup.mapper;

import edu.eci.dosw.techcup.dto.PlayerDTO;
import edu.eci.dosw.techcup.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDto(Player player) {
        if (player == null) return null;
        
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setEmail(player.getEmail());
        dto.setName(player.getName());
        dto.setDorsal(player.getDorsal());
        dto.setPosition(player.getPosition());
        dto.setParticipantType(player.getParticipantType());
        dto.setSemester(player.getSemester());
        dto.setAvailable(player.isAvailable());
        dto.setRole(player.getRole());
        dto.setState(player.getState());
        
        return dto;
    }

    public Player toEntity(PlayerDTO dto) {
        if (dto == null) return null;
        
        Player player = new Player(
            dto.getId(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getName(),
            dto.getDorsal(),
            dto.getPosition(),
            dto.getParticipantType()
        );
        player.setSemester(dto.getSemester());
        player.setAvailable(dto.isAvailable());
        player.setRole(dto.getRole());
        player.setState(dto.getState());
        
        return player;
    }
}