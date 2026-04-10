package edu.eci.dosw.techcup.mapper;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.entity.MemberState;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setState(user.getState());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        
        User user = new Manager(dto.getId(), dto.getEmail(), dto.getPassword());
        user.setRole(dto.getRole() != null ? dto.getRole() : UserRole.JUGADOR);
        user.setState(dto.getState() != null ? dto.getState() : MemberState.ACTIVE);
        return user;
    }
    
    public User toEntity(String email, String password) {
        User user = new Manager(null, email, password);
        user.setRole(UserRole.JUGADOR);
        user.setState(MemberState.ACTIVE);
        return user;
    }
}