package edu.eci.dosw.techcup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class UserServiceTest {

    private UserService userService;

    private static final Long EXISTING_ID     = 1L;
    private static final Long NON_EXISTING_ID = 999L;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(passwordEncoder);
    }

    // ─── RF-01 ────────────────────────────────────────────────────────────────

    @Test
    public void shouldRegisterUserWithEciEmail() {

        String email = "juan.velez@escuelaing.edu.co";


        UserDTO user = userService.registerUser(email, "pass123");

     
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(MemberState.ACTIVE, user.getState());
        assertEquals(UserRole.JUGADOR, user.getRole());
    }

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    public void shouldNotRegisterUserWithInvalidEmailDomain() {
        // Having / When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser("usuario@hotmail.com", "pass")
        );
    }

    @Test
    public void shouldNotRegisterUserWithNullEmail() {
        // Having / When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(null, "pass")
        );
    }

    @Test
    public void shouldNotRegisterDuplicateEmail() {
        // Having
        String email = "nuevo@escuelaing.edu.co";
        userService.registerUser(email, "pass1");

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(email, "pass2")
        );
    }


    @Test
    public void shouldGetAllRegisteredUsers() {
        // Having — 2 dummy + 3 nuevos = 5
        userService.registerUser("ana@escuelaing.edu.co", "p1");
        userService.registerUser("pablo@gmail.com", "p2");
        userService.registerUser("sara@escuelaing.edu.co", "p3");

        // When & Then
        assertEquals(5, userService.getAllUsers().size());
    }

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
        
        User newUser = new Manager(null, email, password);
        newUser.setRole(UserRole.JUGADOR);
        newUser.setState(MemberState.ACTIVE);
        
        User savedUser = new Manager(2L, email, password);
        savedUser.setRole(UserRole.JUGADOR);
        savedUser.setState(MemberState.ACTIVE);
        
        UserDTO expectedDTO = new UserDTO(2L, email, UserRole.JUGADOR, MemberState.ACTIVE);
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.toEntity(email, password)).thenReturn(newUser);
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
        String password = "password123";
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        
        assertThrows(TechCupException.InvalidEmailException.class, () -> {
            userService.registerUser(email, password);
        });
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
        
        assertThrows(TechCupException.ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    @DisplayName("Listar todos los usuarios")
    void getAllUsers() {
        User user2 = new Manager(2L, "user2@escuelaing.edu.co", "password");
        user2.setRole(UserRole.JUGADOR);
        user2.setState(MemberState.ACTIVE);
        
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
}