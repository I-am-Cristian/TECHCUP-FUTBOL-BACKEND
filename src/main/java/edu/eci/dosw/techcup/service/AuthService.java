package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO login(LoginRequestDTO request) {
        logger.info("Intento de login para: {}", request.getEmail());

        if (request.getEmail() == null || request.getPassword() == null) {
            logger.warn("Email o password nulos");
            return null;
        }

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            logger.warn("Credenciales inválidas para: {}", request.getEmail());
            return null;
        }

        if (user.getState() == MemberState.UNACTIVE || user.getState() == MemberState.SUSPENDED) {
            logger.warn("Usuario inactivo intentó autenticarse: {}", request.getEmail());
            return null;
        }

        logger.info("Login exitoso para usuario id: {}", user.getId());
        return userMapper.toDto(user);
    }

    public void logout(String email) {
        logger.info("Logout solicitado para: {}", email);
        // Sprint 2: invalidar token JWT
    }
}