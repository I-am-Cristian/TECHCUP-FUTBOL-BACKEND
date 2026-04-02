package edu.dosw.techcup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.dosw.techcup.model.user.MemberState;
import edu.dosw.techcup.model.user.User;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }


    @Test
    public void shouldRegisterUserWithEciEmail() {
        // Having
        String email = "juan.velez@escuelaing.edu.co";

        // When
        User user = userService.registerUser(1L, email, "pass123");

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(MemberState.ACTIVE, user.getState());
        assertEquals(1, userService.getUsers().size());
    }

    @Test
    public void shouldRegisterUserWithGmailEmail() {
        // Having
        String email = "jugador.techcup@gmail.com";

        // When
        User user = userService.registerUser(2L, email, "pass456");

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(1, userService.getUsers().size());
    }

    @Test
    public void shouldNotRegisterUserWithInvalidEmailDomain() {
        // Having
        String invalidEmail = "usuario@hotmail.com";

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(3L, invalidEmail, "pass789")
        );
        assertEquals(0, userService.getUsers().size());
    }

    @Test
    public void shouldNotRegisterUserWithNullEmail() {
        // Having / When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(4L, null, "pass000")
        );
    }

    @Test
    public void shouldNotRegisterDuplicateEmail() {
        // Having
        String email = "cristian@escuelaing.edu.co";
        userService.registerUser(5L, email, "pass111");

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(6L, email, "pass222")
        );
        assertEquals(1, userService.getUsers().size());
    }


    @Test
    public void shouldGetAllRegisteredUsers() {
        // Having
        userService.registerUser(10L, "ana@escuelaing.edu.co", "p1");
        userService.registerUser(11L, "pablo@gmail.com", "p2");
        userService.registerUser(12L, "sara@escuelaing.edu.co", "p3");

        // When & Then
        assertEquals(3, userService.getUsers().size());
    }

    @Test
    public void shouldGetUserById() {
        // Having
        userService.registerUser(20L, "admin@escuelaing.edu.co", "adminpass");

        // When
        User found = userService.getUserById(20L);

        // Then
        assertNotNull(found);
        assertEquals("admin@escuelaing.edu.co", found.getEmail());
    }

    @Test
    public void shouldReturnNullForNonExistentUserId() {
        // Having / When
        User found = userService.getUserById(999L);

        // Then
        assertNull(found);
    }

    @Test
    public void shouldGetUserByEmail() {
        // Having
        userService.registerUser(30L, "tecnico@gmail.com", "pass");

        // When
        User found = userService.getUserByEmail("tecnico@gmail.com");

        // Then
        assertNotNull(found);
        assertEquals(30L, found.getId());
    }

    @Test
    public void shouldInactivateUser() {
        // Having
        userService.registerUser(40L, "inactivo@escuelaing.edu.co", "pass");

        // When
        userService.inactivateUser(40L);

        // Then
        assertEquals(MemberState.UNACTIVE, userService.getUserById(40L).getState());
    }

    @Test
    public void shouldSuspendUser() {
        // Having
        userService.registerUser(50L, "suspendido@gmail.com", "pass");

        // When
        userService.suspendUser(50L);

        // Then
        assertEquals(MemberState.SUSPENDED, userService.getUserById(50L).getState());
    }

    @Test
    public void shouldActivateInactiveUser() {
        // Having
        userService.registerUser(60L, "reactivo@escuelaing.edu.co", "pass");
        userService.inactivateUser(60L);

        // When
        userService.activateUser(60L);

        // Then
        assertEquals(MemberState.ACTIVE, userService.getUserById(60L).getState());
    }

    @Test
    public void shouldThrowExceptionWhenInactivatingNonExistentUser() {
        // Having / When & Then
        assertThrows(IllegalArgumentException.class, () ->
                userService.inactivateUser(999L)
        );
    }
}
