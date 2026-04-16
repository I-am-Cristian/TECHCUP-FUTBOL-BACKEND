package edu.eci.dosw.techcup.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String ECI_DOMAIN = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    private final Map<Long, User> users = new LinkedHashMap<>();
    private long idCounter = 1L;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        
        User admin = new Manager(idCounter++, "admin@escuelaing.edu.co", 
            passwordEncoder.encode("admin123"));
        admin.setRole(UserRole.ADMINISTRADOR);
        users.put(admin.getId(), admin);

        User jugador = new Manager(idCounter++, "jugador@gmail.com", 
            passwordEncoder.encode("jugador123"));
        users.put(jugador.getId(), jugador);
    }

    public UserDTO registerUser(String email, String password) {
        logger.info("Registrando usuario: {}", email);
        validateEmail(email);
        User newUser = new Manager(idCounter++, email, passwordEncoder.encode(password));
        users.put(newUser.getId(), newUser);
        logger.info("Usuario registrado con id: {}", newUser.getId());
        return toDTO(newUser);
    }

    /** Registro desde Tests — id explícito para controlar el comportamiento. */
    public UserDTO registerUser(Long id, String email, String password) {
        logger.info("Registrando usuario con id explícito: {}", id);
        validateEmail(email);
        User newUser = new Manager(id, email, passwordEncoder.encode(password));
        users.put(newUser.getId(), newUser);
        return toDTO(newUser);
    }

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
        return toDTO(user);
    }

    // ─── Spring Security ──────────────────────────────────────────────────────

    /**
     * a. ¿Cuál es el objetivo del método loadUserByEmail?
     *    Cargar la información de un usuario desde el repositorio usando su email
     *    y convertirla en un objeto UserDetails que Spring Security puede usar
     *    para autenticación y autorización.
     * 
     * b. ¿Para qué sirve la clase UserDetails?
     *    Es una interfaz de Spring Security que representa los datos principales
     *    de un usuario autenticado (username, password, authorities). Spring Security
     *    la usa internamente para validar credenciales y gestionar permisos.
     * 
     * c. ¿Para qué sirve SimpleGrantedAuthority?
     *    Representa un permiso o autoridad concedida a un usuario. Se usa para
     *    implementar control de acceso basado en roles (RBAC). Cada autoridad
     *    es un String que identifica un permiso específico.
     */
    public UserDetails loadUserByEmail(String email) {
        User user = users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
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
        if (email == null || (!email.endsWith(ECI_DOMAIN) && !email.endsWith(GMAIL_DOMAIN))) {
            throw new TechCupException.InvalidEmailException(
                    "Dominio no permitido. Use @escuelaing.edu.co o @gmail.com");
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            throw new TechCupException.InvalidEmailException(
                    "El correo ya está registrado: " + email);
        }
    }
}