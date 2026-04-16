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
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;

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

    @Test
    public void shouldRegisterUserWithGmailEmail() {
        // Having
        String email = "jugador@gmail.com";

        // When — gmail ya existe en dummy, usamos uno diferente
        UserDTO user = userService.registerUser("nuevo.jugador@gmail.com", "pass");

        // Then
        assertNotNull(user);
    }

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

    @Test
    public void shouldGetUserById() {
        // Having / When
        UserDTO found = userService.getUserById(EXISTING_ID);

        // Then
        assertNotNull(found);
        assertEquals(EXISTING_ID, found.getId());
    }

    @Test
    public void shouldThrowExceptionForNonExistentUserId() {
        // Having / When & Then
        assertThrows(TechCupException.ResourceNotFoundException.class, () ->
                userService.getUserById(NON_EXISTING_ID)
        );
    }

    @Test
    public void shouldGetUserByEmail() {
        // Having
        userService.registerUser("tecnico@gmail.com", "pass");

        // When
        UserDTO found = userService.getUserByEmail("tecnico@gmail.com");

        // Then
        assertNotNull(found);
        assertEquals("tecnico@gmail.com", found.getEmail());
    }

    @Test
    public void shouldReturnNullForNonExistentEmail() {
        // Having / When
        UserDTO found = userService.getUserByEmail("noexiste@gmail.com");

        // Then
        assertNull(found);
    }

    @Test
    public void shouldInactivateUser() {
        // Having / When
        userService.inactivateUser(EXISTING_ID);

        // Then
        assertEquals(MemberState.UNACTIVE,
                userService.getUserById(EXISTING_ID).getState());
    }

    @Test
    public void shouldSuspendUser() {
        // Having / When
        userService.suspendUser(EXISTING_ID);

        // Then
        assertEquals(MemberState.SUSPENDED,
                userService.getUserById(EXISTING_ID).getState());
    }

    @Test
    public void shouldActivateInactiveUser() {
        // Having
        userService.inactivateUser(EXISTING_ID);

        // When
        userService.activateUser(EXISTING_ID);

        // Then
        assertEquals(MemberState.ACTIVE,
                userService.getUserById(EXISTING_ID).getState());
    }

    @Test
    public void shouldChangeUserRole() {
        // Having / When
        userService.changeUserRole(EXISTING_ID, UserRole.ORGANIZADOR);

        // Then
        assertEquals(UserRole.ORGANIZADOR,
                userService.getUserById(EXISTING_ID).getRole());
    }

    @Test
    public void shouldThrowExceptionWhenInactivatingNonExistentUser() {
        // Having / When & Then
        assertThrows(TechCupException.ResourceNotFoundException.class, () ->
                userService.inactivateUser(NON_EXISTING_ID)
        );
    }
}