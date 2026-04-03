package edu.eci.dosw.techcup.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.entity.UserRole;
import edu.eci.dosw.techcup.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * RF-01: Registro | RF-04: Gestión de usuarios.
 * No existe DELETE — solo inactivar.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Registro y gestión de usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "RF-04: Retorna todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista obtenida")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "No encontrado")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario",
               description = "RF-01: Crea usuario con rol JUGADOR por defecto")
    @ApiResponse(responseCode = "201", description = "Usuario registrado")
    @ApiResponse(responseCode = "400", description = "Email inválido o ya registrado")
    public ResponseEntity<UserDTO> register(@RequestBody Map<String, String> body) {
        UserDTO created = userService.registerUser(
                body.get("email"), body.get("password"));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Inactivar usuario",
               description = "RF-04: Cambia estado a UNACTIVE. No hay DELETE.")
    @ApiResponse(responseCode = "200", description = "Usuario inactivado")
    @ApiResponse(responseCode = "404", description = "No encontrado")
    public ResponseEntity<UserDTO> inactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.inactivateUser(id));
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "Cambiar rol",
               description = "RF-04: Solo el administrador puede cambiar el rol")
    @ApiResponse(responseCode = "200", description = "Rol actualizado")
    @ApiResponse(responseCode = "404", description = "No encontrado")
    public ResponseEntity<UserDTO> changeRole(@PathVariable Long id,
                                              @RequestParam UserRole newRole) {
        return ResponseEntity.ok(userService.changeUserRole(id, newRole));
    }
}