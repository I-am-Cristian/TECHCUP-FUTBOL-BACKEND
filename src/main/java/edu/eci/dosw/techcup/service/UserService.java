package edu.eci.dosw.techcup.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String ECI_DOMAIN = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(String email, String password) {
        logger.info("Registrando usuario: {}", email);
        validateEmail(email);
        User newUser = new Manager(null, email, passwordEncoder.encode(password));
        User saved = userRepository.save(newUser);
        return userMapper.toDto(saved);
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
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDTO inactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.UNACTIVE);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO suspendUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.SUSPENDED);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setState(MemberState.ACTIVE);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO changeUserRole(Long id, UserRole newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TechCupException.ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
        user.setRole(newRole);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
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