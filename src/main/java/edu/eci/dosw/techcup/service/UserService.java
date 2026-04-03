package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio de gestión de usuarios.
 * RF-01: Registro de usuario (dominio @escuelaing.edu.co o @gmail.com).
 * RF-04: Gestión de usuarios — listar, inactivar, suspender, cambiar rol.
 * Nota: Sin persistencia en este ciclo — datos en memoria (dummy).
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String ECI_DOMAIN   = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    private final Map<Long, User> users = new LinkedHashMap<>();
    private long idCounter = 1L;

    public UserService() {
        // Dummy data para pruebas del API
        User admin = new Manager(idCounter++, "admin@escuelaing.edu.co", "admin123");
        admin.setRole(UserRole.ADMINISTRADOR);
        users.put(admin.getId(), admin);

        User jugador = new Manager(idCounter++, "jugador@gmail.com", "jugador123");
        users.put(jugador.getId(), jugador);
    }

    // ─── RF-01: Registro ──────────────────────────────────────────────────────

    /**
     * Registra un nuevo usuario. El id se genera automáticamente.
     * Usado por el Controller — RF-01.
     */
    public UserDTO registerUser(String email, String password) {
        logger.info("Intentando registrar usuario con correo: {}", email);
        validateEmail(email);

        User newUser = new Manager(idCounter++, email, password);
        users.put(newUser.getId(), newUser);
        logger.info("Usuario registrado con id: {}", newUser.getId());
        return toDTO(newUser);
    }

    /**
     * Registra un nuevo usuario con id explícito.
     * Usado por los tests — permite controlar el id para verificar comportamiento.
     */
    public User registerUser(Long id, String email, String password) {
        logger.info("Registrando usuario con id explícito: {}", id);
        validateEmail(email);

        User newUser = new Manager(id, email, password);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    // ─── RF-04: Gestión ───────────────────────────────────────────────────────

    public List<UserDTO> getAllUsers() {
        logger.info("Consultando lista de usuarios. Total: {}", users.size());
        List<UserDTO> result = new ArrayList<>();
        users.values().forEach(u -> result.add(toDTO(u)));
        return result;
    }

    public UserDTO getUserById(Long id) {
        logger.info("Consultando usuario con id: {}", id);
        User user = findOrThrow(id);
        return toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        logger.info("Consultando usuario con email: {}", email);
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(this::toDTO)
                .orElse(null);
    }

    public UserDTO inactivateUser(Long id) {
        logger.info("Inactivando usuario con id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.UNACTIVE);
        logger.info("Usuario {} inactivado", id);
        return toDTO(user);
    }

    public UserDTO suspendUser(Long id) {
        logger.info("Suspendiendo usuario con id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.SUSPENDED);
        logger.info("Usuario {} suspendido", id);
        return toDTO(user);
    }

    public UserDTO activateUser(Long id) {
        logger.info("Activando usuario con id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.ACTIVE);
        logger.info("Usuario {} activado", id);
        return toDTO(user);
    }

    public UserDTO changeUserRole(Long id, UserRole newRole) {
        logger.info("Cambiando rol del usuario {} a {}", id, newRole);
        User user = findOrThrow(id);
        user.setRole(newRole);
        logger.info("Rol del usuario {} actualizado a {}", id, newRole);
        return toDTO(user);
    }

    // ─── Helpers privados ─────────────────────────────────────────────────────

    private User findOrThrow(Long id) {
        User user = users.get(id);
        if (user == null) {
            logger.error("Usuario no encontrado con id: {}", id);
            throw new TechCupException.ResourceNotFoundException(
                    "Usuario no encontrado con id: " + id);
        }
        return user;
    }

    private void validateEmail(String email) {
        if (email == null || (!email.endsWith(ECI_DOMAIN) && !email.endsWith(GMAIL_DOMAIN))) {
            logger.warn("Email inválido: {}", email);
            throw new IllegalArgumentException(
                    "Dominio no permitido. Use @escuelaing.edu.co o @gmail.com");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            logger.warn("Email ya registrado: {}", email);
            throw new IllegalArgumentException("El correo ya está registrado: " + email);
        }
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getRole(), user.getState());
    }
}
