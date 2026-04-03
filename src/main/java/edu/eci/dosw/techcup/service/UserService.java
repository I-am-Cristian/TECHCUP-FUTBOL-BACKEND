package edu.eci.dosw.techcup.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;

/**
 * Servicio de gestión de usuarios.
 * RF-01: Registro de usuario.
 * RF-04: Gestión — listar, inactivar, suspender, cambiar rol.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String ECI_DOMAIN   = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    private final Map<Long, User> users = new LinkedHashMap<>();
    private long idCounter = 1L;

    public UserService() {
        User admin = new Manager(idCounter++, "admin@escuelaing.edu.co", "admin123");
        admin.setRole(UserRole.ADMINISTRADOR);
        users.put(admin.getId(), admin);

        User jugador = new Manager(idCounter++, "jugador@gmail.com", "jugador123");
        users.put(jugador.getId(), jugador);
    }

    // ─── RF-01 ────────────────────────────────────────────────────────────────

    /** Registro desde el Controller — id auto-generado. */
    public UserDTO registerUser(String email, String password) {
        logger.info("Registrando usuario: {}", email);
        validateEmail(email);
        User newUser = new Manager(idCounter++, email, password);
        users.put(newUser.getId(), newUser);
        logger.info("Usuario registrado con id: {}", newUser.getId());
        return toDTO(newUser);
    }

    /** Registro desde Tests — id explícito para controlar el comportamiento. */
    public UserDTO registerUser(Long id, String email, String password) {
        logger.info("Registrando usuario con id explícito: {}", id);
        validateEmail(email);
        User newUser = new Manager(id, email, password);
        users.put(newUser.getId(), newUser);
        return toDTO(newUser);
    }

    // ─── RF-04 ────────────────────────────────────────────────────────────────

    public List<UserDTO> getAllUsers() {
        logger.info("Listando usuarios. Total: {}", users.size());
        List<UserDTO> result = new ArrayList<>();
        users.values().forEach(u -> result.add(toDTO(u)));
        return result;
    }

    public UserDTO getUserById(Long id) {
        logger.info("Buscando usuario id: {}", id);
        return toDTO(findOrThrow(id));
    }

    public UserDTO getUserByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(this::toDTO)
                .orElse(null);
    }

    public UserDTO inactivateUser(Long id) {
        logger.info("Inactivando usuario id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.UNACTIVE);
        return toDTO(user);
    }

    public UserDTO suspendUser(Long id) {
        logger.info("Suspendiendo usuario id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.SUSPENDED);
        return toDTO(user);
    }

    public UserDTO activateUser(Long id) {
        logger.info("Activando usuario id: {}", id);
        User user = findOrThrow(id);
        user.setState(MemberState.ACTIVE);
        return toDTO(user);
    }

    public UserDTO changeUserRole(Long id, UserRole newRole) {
        logger.info("Cambiando rol usuario {} a {}", id, newRole);
        User user = findOrThrow(id);
        user.setRole(newRole);
        return toDTO(user);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private User findOrThrow(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new TechCupException.ResourceNotFoundException(
                    "Usuario no encontrado con id: " + id);
        }
        return user;
    }

    private void validateEmail(String email) {
        if (email == null
                || (!email.endsWith(ECI_DOMAIN) && !email.endsWith(GMAIL_DOMAIN))) {
            throw new IllegalArgumentException(
                    "Dominio no permitido. Use @escuelaing.edu.co o @gmail.com");
        }
        if (users.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException(
                    "El correo ya está registrado: " + email);
        }
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(),
                user.getRole(), user.getState());
    }
}