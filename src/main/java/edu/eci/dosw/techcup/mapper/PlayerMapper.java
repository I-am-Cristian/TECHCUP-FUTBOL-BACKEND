package edu.eci.dosw.techcup.mapper;

import edu.eci.dosw.techcup.dto.PlayerDTO;
import edu.eci.dosw.techcup.entity.Player;
import edu.eci.dosw.techcup.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDto(Player player) {
        if (player == null) return null;
        
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setDorsal(player.getDorsal());
        dto.setPosition(player.getPosition());
        dto.setParticipantType(player.getParticipantType());
        dto.setSemester(player.getSemester());
        dto.setAvailable(player.isAvailable());
        if (player.getUser() != null) {
            dto.setUserId(player.getUser().getId());
        }
        return dto;
    }

    public Player toEntity(PlayerDTO dto) {
        if (dto == null) return null;
        
        Player player = new Player();
        player.setId(dto.getId());
        player.setName(dto.getName());
        player.setDorsal(dto.getDorsal());
        player.setPosition(dto.getPosition());
        player.setParticipantType(dto.getParticipantType());
        player.setSemester(dto.getSemester());
        player.setAvailable(dto.isAvailable());
        
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            player.setUser(user);
        }
        return player;
    }
}