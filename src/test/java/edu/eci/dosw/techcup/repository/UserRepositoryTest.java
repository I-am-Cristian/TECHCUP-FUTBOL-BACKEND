package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.Manager;
import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Debe guardar un usuario correctamente")
    void shouldSaveUser() {
        User user = new Manager(null, "test@gmail.com", "123456");
        user.setRole(UserRole.JUGADOR);
        user.setState(MemberState.ACTIVE);

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("test@gmail.com", saved.getEmail());
        assertEquals(UserRole.JUGADOR, saved.getRole());
    }

    @Test
    @DisplayName("Debe buscar usuario por email")
    void shouldFindByEmail() {
        User user = new Manager(null, "buscar@gmail.com", "123456");
        user.setRole(UserRole.JUGADOR);
        user.setState(MemberState.ACTIVE);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("buscar@gmail.com");

        assertTrue(found.isPresent());
        assertEquals("buscar@gmail.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Debe buscar usuarios por rol")
    void shouldFindByRole() {
        User u1 = new Manager(null, "admin@gmail.com", "123456");
        u1.setRole(UserRole.ADMINISTRADOR);
        u1.setState(MemberState.ACTIVE);

        User u2 = new Manager(null, "jugador@gmail.com", "123456");
        u2.setRole(UserRole.JUGADOR);
        u2.setState(MemberState.ACTIVE);

        userRepository.save(u1);
        userRepository.save(u2);

        List<User> admins = userRepository.findByRole(UserRole.ADMINISTRADOR);
        assertEquals(1, admins.size());
    }

    @Test
    @DisplayName("Debe actualizar el estado de un usuario")
    void shouldUpdateUserState() {
        User user = new Manager(null, "update@gmail.com", "123456");
        user.setRole(UserRole.JUGADOR);
        user.setState(MemberState.ACTIVE);
        User saved = userRepository.save(user);

        saved.setState(MemberState.UNACTIVE);
        User updated = userRepository.save(saved);

        assertEquals(MemberState.UNACTIVE, updated.getState());
    }
}