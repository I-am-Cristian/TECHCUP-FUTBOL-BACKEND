package edu.eci.dosw.techcup.mapper;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getRole(),
            user.getState()
        );
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setState(dto.getState());
        return user;
    }
}