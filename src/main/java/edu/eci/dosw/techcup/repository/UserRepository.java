package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.User;
import edu.eci.dosw.techcup.entity.UserRole;    // ← Import para Role
import edu.eci.dosw.techcup.entity.MemberState;  // ← Import para State (o el nombre de tu enum)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;  // ← Import para Optional

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar por email (retorna Optional)
    Optional<User> findByEmail(String email);
    
    // Buscar por rol
    List<User> findByRole(UserRole role);
    
    // Buscar por estado
    List<User> findByState(MemberState state);
    
    // Buscar por nombre (si tu entidad tiene campo "name")
    // List<User> findByName(String name);
}