package edu.eci.dosw.techcup.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.UserRole;

public class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    // ─── RF-02 ────────────────────────────────────────────────────────────────

    @Test
    public void shouldLoginWithValidAdminCredentials() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(
                "admin@escuelaing.edu.co", "admin123");

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNotNull(result);
        assertEquals("admin@escuelaing.edu.co", result.getEmail());
        assertEquals(UserRole.ADMINISTRADOR, result.getRole());
    }

    @Test
    public void shouldLoginWithValidPlayerCredentials() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(
                "jugador@gmail.com", "jugador123");

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNotNull(result);
        assertEquals(UserRole.JUGADOR, result.getRole());
        assertEquals(MemberState.ACTIVE, result.getState());
    }

    @Test
    public void shouldNotLoginWithWrongPassword() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(
                "admin@escuelaing.edu.co", "wrongpassword");

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldNotLoginWithNonExistentEmail() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(
                "noexiste@gmail.com", "pass123");

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldNotLoginWithNullEmail() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(null, "pass123");

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldNotLoginWithNullPassword() {
        // Having
        LoginRequestDTO request = new LoginRequestDTO(
                "admin@escuelaing.edu.co", null);

        // When
        UserDTO result = authService.login(request);

        // Then
        assertNull(result);
    }

    // ─── RF-03 ────────────────────────────────────────────────────────────────

    @Test
    public void shouldLogoutWithoutError() {
        // Having / When & Then
        assertDoesNotThrow(() ->
                authService.logout("admin@escuelaing.edu.co")
        );
    }

    @Test
    public void shouldLogoutNonExistentUserWithoutError() {
        // Having / When & Then — logout no requiere que el usuario exista
        assertDoesNotThrow(() ->
                authService.logout("fantasma@gmail.com")
        );
    }
}