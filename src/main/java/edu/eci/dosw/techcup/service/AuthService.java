package edu.eci.dosw.techcup.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;

/**
 * Servicio de autenticación.
 * RF-02: Login con correo y contraseña.
 * RF-03: Cierre de sesión.
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final Map<String, User> usersByEmail = new LinkedHashMap<>();

    public AuthService() {
        User admin = new Manager(1L, "admin@escuelaing.edu.co", "admin123");
        admin.setRole(UserRole.ADMINISTRADOR);
        usersByEmail.put(admin.getEmail(), admin);

        User jugador = new Manager(2L, "jugador@gmail.com", "jugador123");
        usersByEmail.put(jugador.getEmail(), jugador);
    }

    public UserDTO login(LoginRequestDTO request) {
        logger.info("Intento de login para: {}", request.getEmail());

        if (request.getEmail() == null || request.getPassword() == null) {
            logger.warn("Email o password nulos");
            return null;
        }

        User user = usersByEmail.get(request.getEmail());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            logger.warn("Credenciales inválidas para: {}", request.getEmail());
            return null;
        }

        if (user.getState() == MemberState.UNACTIVE
                || user.getState() == MemberState.SUSPENDED) {
            logger.warn("Usuario inactivo intentó autenticarse: {}", request.getEmail());
            return null;
        }

        logger.info("Login exitoso para usuario id: {}", user.getId());
        return new UserDTO(user.getId(), user.getEmail(), user.getRole(), user.getState());
    }

    public void logout(String email) {
        logger.info("Logout solicitado para: {}", email);
        // Sprint 2: invalidar token JWT
    }
}