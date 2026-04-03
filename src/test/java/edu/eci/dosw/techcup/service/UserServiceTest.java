package edu.eci.dosw.techcup.service;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.exception.TechCupException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // RF-01 REGISTER

    @Test
    void shouldRegisterUserWithValidECIDomain() {
        UserDTO user = userService.registerUser(
                "nuevo@escuelaing.edu.co",
                "1234"
        );

        assertNotNull(user);
        assertEquals("nuevo@escuelaing.edu.co", user.getEmail());
        assertEquals(MemberState.ACTIVE, user.getState());
    }

    @Test
    void shouldRegisterUserWithValidGmailDomain() {
        UserDTO user = userService.registerUser(
                "correo@gmail.com",
                "abcd"
        );

        assertNotNull(user);
        assertEquals("correo@gmail.com", user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenDomainIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(
                    "correo@yahoo.com",
                    "1234"
            );
        });
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        userService.registerUser("repetido@gmail.com", "1234");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("repetido@gmail.com", "abcd");
        });
    }


    // GET USERS

    @Test
    void shouldReturnAllUsersIncludingDummyData() {
        List<UserDTO> users = userService.getAllUsers();

        // 2 dummy users creados en constructor
        assertTrue(users.size() >= 2);
    }

    @Test
    void shouldReturnUserById() {
        UserDTO user = userService.getUserById(1L);

        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserIdNotFound() {
        assertThrows(TechCupException.ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    void shouldReturnUserByEmail() {
        UserDTO user = userService.getUserByEmail("admin@escuelaing.edu.co");

        assertNotNull(user);
        assertEquals("admin@escuelaing.edu.co", user.getEmail());
    }

    @Test
    void shouldReturnNullWhenEmailNotFound() {
        UserDTO user = userService.getUserByEmail("noexiste@gmail.com");

        assertNull(user);
    }


    // STATE MANAGEMENT

    @Test
    void shouldInactivateUser() {
        UserDTO updated = userService.inactivateUser(1L);

        assertEquals(MemberState.UNACTIVE, updated.getState());
    }

    @Test
    void shouldSuspendUser() {
        UserDTO updated = userService.suspendUser(1L);

        assertEquals(MemberState.SUSPENDED, updated.getState());
    }

    @Test
    void shouldActivateUser() {
        userService.suspendUser(1L);

        UserDTO updated = userService.activateUser(1L);

        assertEquals(MemberState.ACTIVE, updated.getState());
    }

    @Test
    void shouldThrowExceptionWhenChangingStateOfNonExistingUser() {
        assertThrows(TechCupException.ResourceNotFoundException.class, () -> {
            userService.inactivateUser(999L);
        });
    }


    // ROLE MANAGEMENT

    @Test
    void shouldChangeUserRole() {
        UserDTO updated = userService.changeUserRole(1L, UserRole.ORGANIZADOR);

        assertEquals(UserRole.ORGANIZADOR, updated.getRole());
    }

    @Test
    void shouldThrowExceptionWhenChangingRoleOfNonExistingUser() {
        assertThrows(TechCupException.ResourceNotFoundException.class, () -> {
            userService.changeUserRole(999L, UserRole.ADMINISTRADOR);
        });
    }
}