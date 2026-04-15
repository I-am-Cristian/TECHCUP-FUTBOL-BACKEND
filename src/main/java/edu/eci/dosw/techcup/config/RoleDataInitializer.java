// src/main/java/edu/eci/dosw/techcup/config/RoleDataInitializer.java
package edu.eci.dosw.techcup.config;

import edu.eci.dosw.techcup.entity.*;
import edu.eci.dosw.techcup.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Map;

@Component
public class RoleDataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    
    public RoleDataInitializer(RoleRepository roleRepository, 
                               PermissionRepository permissionRepository,
                               UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional
    public void run(String... args) {
        // 1. Crear permisos
        Permission createTournament = new Permission("CREATE_TOURNAMENT", "Puede crear torneos");
        Permission editTournament = new Permission("EDIT_TOURNAMENT", "Puede editar torneos");
        Permission deleteTournament = new Permission("DELETE_TOURNAMENT", "Puede eliminar torneos");
        Permission viewReports = new Permission("VIEW_REPORTS", "Puede ver reportes");
        Permission manageUsers = new Permission("MANAGE_USERS", "Puede gestionar usuarios");
        Permission registerPlayer = new Permission("REGISTER_PLAYER", "Puede registrar jugadores");
        
        permissionRepository.saveAll(Arrays.asList(
            createTournament, editTournament, deleteTournament,
            viewReports, manageUsers, registerPlayer
        ));
        
        // 2. Crear roles (mapeando desde UserRole enum)
        Role jugador = new Role("JUGADOR", "Jugador regular");
        jugador.getPermissions().add(registerPlayer);
        
        Role capitan = new Role("CAPITAN", "Capitán de equipo");
        capitan.getPermissions().addAll(Arrays.asList(registerPlayer, viewReports));
        
        Role organizador = new Role("ORGANIZADOR", "Organizador de torneos");
        organizador.getPermissions().addAll(Arrays.asList(
            createTournament, editTournament, viewReports, registerPlayer
        ));
        
        Role arbitro = new Role("ARBITRO", "Árbitro");
        arbitro.getPermissions().add(viewReports);
        
        Role administrador = new Role("ADMINISTRADOR", "Administrador del sistema");
        administrador.getPermissions().addAll(Arrays.asList(
            createTournament, editTournament, deleteTournament,
            viewReports, manageUsers, registerPlayer
        ));
        
        roleRepository.saveAll(Arrays.asList(
            jugador, capitan, organizador, arbitro, administrador
        ));
        
        System.out.println("Roles y permisos inicializados correctamente");
    }
}