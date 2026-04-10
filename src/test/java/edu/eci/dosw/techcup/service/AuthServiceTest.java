package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.mapper.UserMapper;
import edu.eci.dosw.techcup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "test@escuelaing.edu.co", "password123") {};
        testUser.setRole(UserRole.JUGADOR);
        testUser.setState(MemberState.ACTIVE);

        testUserDTO = new UserDTO(1L, "test@escuelaing.edu.co", UserRole.JUGADOR, MemberState.ACTIVE);
    }

    @Test
    @DisplayName("Login exitoso con credenciales correctas")
    void loginSuccess() {
        LoginRequestDTO request = new LoginRequestDTO("test@escuelaing.edu.co", "password123");
        
        when(userRepository.findByEmail("test@escuelaing.edu.co")).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDTO);
        
        UserDTO result = authService.login(request);
        
        assertNotNull(result);
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userRepository).findByEmail("test@escuelaing.edu.co");
    }

    @Test
    @DisplayName("Login falla con contraseña incorrecta")
    void loginFailWrongPassword() {
        LoginRequestDTO request = new LoginRequestDTO("test@escuelaing.edu.co", "wrongpassword");
        
        when(userRepository.findByEmail("test@escuelaing.edu.co")).thenReturn(Optional.of(testUser));
        
        UserDTO result = authService.login(request);
        
        assertNull(result);
    }

    @Test
    @DisplayName("Login falla con email no registrado")
    void loginFailEmailNotFound() {
        LoginRequestDTO request = new LoginRequestDTO("nonexistent@escuelaing.edu.co", "password123");
        
        when(userRepository.findByEmail("nonexistent@escuelaing.edu.co")).thenReturn(Optional.empty());
        
        UserDTO result = authService.login(request);
        
        assertNull(result);
    }

    @Test
    @DisplayName("Login falla con usuario inactivo")
    void loginFailInactiveUser() {
        testUser.setState(MemberState.UNACTIVE);
        LoginRequestDTO request = new LoginRequestDTO("test@escuelaing.edu.co", "password123");
        
        when(userRepository.findByEmail("test@escuelaing.edu.co")).thenReturn(Optional.of(testUser));
        
        UserDTO result = authService.login(request);
        
        assertNull(result);
    }
}