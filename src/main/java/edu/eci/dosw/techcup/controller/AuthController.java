package edu.eci.dosw.techcup.controller;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.service.AuditActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación.
 * RF-02: Login | RF-03: Logout
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y logout de usuarios")
public class AuthController {

    private final AuditActionService AuditActionService;

    public AuthController(AuditActionService AuditActionService) {
        this.AuditActionService = AuditActionService;
    }

    /**
     * RF-02: Autenticar usuario con correo y contraseña.
     * POST /api/auth/login
     */
    @PostMapping("/login")
    @Operation(summary = "Login de usuario",
               description = "Autentica al usuario con correo y contraseña")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO request) {
        UserDTO user = AuditActionService.login(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * RF-03: Cerrar sesión.
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout de usuario",
               description = "Cierra la sesión del usuario")
    @ApiResponse(responseCode = "200", description = "Logout exitoso")
    public ResponseEntity<String> logout(@RequestParam String email) {
        AuditActionService.logout(email);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}