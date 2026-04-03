package edu.eci.dosw.techcup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.UserDTO;
import edu.eci.dosw.techcup.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * RF-02: Login | RF-03: Logout
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y logout de usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "RF-02: Autentica con correo y contraseña")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO request) {
        UserDTO user = authService.login(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "RF-03: Cierra la sesión del usuario")
    @ApiResponse(responseCode = "200", description = "Logout exitoso")
    public ResponseEntity<String> logout(@RequestParam String email) {
        authService.logout(email);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}