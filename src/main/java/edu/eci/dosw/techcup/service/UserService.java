package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String ECI_DOMAIN = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // ─── RF-01: Registro de usuario ────────────────────────────────────────────
    public UserDTO registerUser(String email, String password) {
        logger.info("Registrando usuario: {}", email);
        validateEmail(email);
        
        User newUser = userMapper.toEntity(email, password);
        newUser.setRole(UserRole.JUGADOR);
        newUser.setState(MemberState.ACTIVE);
        
        User savedUser = userRepository.save(newUser);
        logger.info("Usuario registrado con id: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    // ─── RF-04: Gestión de usuarios ────────────────────────────────────────────
    public List<UserDTO> getAllUsers() {
        logger.info("Listando todos los usuarios");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        logger.info("Buscando usuario por id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        return userMapper.toDto(user);
    }

    public UserDTO getUserByEmail(String email) {
        logger.info("Buscando usuario por email: {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDTO inactivateUser(Long id) {
        logger.info("Inactivando usuario id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.UNACTIVE);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserDTO suspendUser(Long id) {
        logger.info("Suspendiendo usuario id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.SUSPENDED);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserDTO activateUser(Long id) {
        logger.info("Activando usuario id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.ACTIVE);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserDTO changeUserRole(Long id, UserRole newRole) {
        logger.info("Cambiando rol usuario {} a {}", id, newRole);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private void validateEmail(String email) {
        if (email == null || (!email.endsWith(ECI_DOMAIN) && !email.endsWith(GMAIL_DOMAIN))) {
            throw new IllegalArgumentException("Dominio no permitido. Use @escuelaing.edu.co o @gmail.com");
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado: " + email);
        }
    }
}