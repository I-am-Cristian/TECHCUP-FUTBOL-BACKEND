package edu.eci.dosw.techcup.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new Manager(1L, "test@escuelaing.edu.co", "password123");
        testUser.setRole(UserRole.JUGADOR);
        testUser.setState(MemberState.ACTIVE);
        testUserDTO = new UserDTO(1L, "test@escuelaing.edu.co", UserRole.JUGADOR, MemberState.ACTIVE);
    }

    @Test
    @DisplayName("Registrar usuario exitosamente")
    void registerUserSuccess() {
        String email = "newuser@escuelaing.edu.co";
        String password = "password123";
        User savedUser = new Manager(2L, email, password);
        savedUser.setRole(UserRole.JUGADOR);
        savedUser.setState(MemberState.ACTIVE);
        UserDTO expectedDTO = new UserDTO(2L, email, UserRole.JUGADOR, MemberState.ACTIVE);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(expectedDTO);

        UserDTO result = userService.registerUser(email, password);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Registrar usuario falla con email ya existente")
    void registerUserDuplicateEmail() {
        String email = "test@escuelaing.edu.co";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        assertThrows(TechCupException.InvalidEmailException.class, () ->
            userService.registerUser(email, "password123")
        );
    }

    @Test
    @DisplayName("Registrar usuario falla con dominio inválido")
    void registerUserInvalidDomain() {
        assertThrows(TechCupException.InvalidEmailException.class, () ->
            userService.registerUser("usuario@hotmail.com", "pass")
        );
    }

    @Test
    @DisplayName("Registrar usuario falla con email nulo")
    void registerUserNullEmail() {
        assertThrows(TechCupException.InvalidEmailException.class, () ->
            userService.registerUser(null, "pass")
        );
    }

    @Test
    @DisplayName("Obtener usuario por ID exitosamente")
    void getUserByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Obtener usuario por ID falla cuando no existe")
    void getUserByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TechCupException.ResourceNotFoundException.class, () ->
            userService.getUserById(999L)
        );
    }

    @Test
    @DisplayName("Listar todos los usuarios")
    void getAllUsers() {
        User user2 = new Manager(2L, "user2@escuelaing.edu.co", "password");
        UserDTO userDTO2 = new UserDTO(2L, "user2@escuelaing.edu.co", UserRole.JUGADOR, MemberState.ACTIVE);

        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));
        when(userMapper.toDto(testUser)).thenReturn(testUserDTO);
        when(userMapper.toDto(user2)).thenReturn(userDTO2);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Inactivar usuario exitosamente")
    void inactivateUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDTO);

        UserDTO result = userService.inactivateUser(1L);

        assertNotNull(result);
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Cambiar rol de usuario exitosamente")
    void changeUserRoleSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDTO);

        UserDTO result = userService.changeUserRole(1L, UserRole.ORGANIZADOR);

        assertNotNull(result);
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Lanzar excepción al inactivar usuario inexistente")
    void inactivateUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TechCupException.ResourceNotFoundException.class, () ->
            userService.inactivateUser(999L)
        );
    }
}