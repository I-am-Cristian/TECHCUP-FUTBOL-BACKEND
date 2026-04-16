package edu.eci.dosw.techcup.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.techcup.dto.LoginRequestDTO;
import edu.eci.dosw.techcup.dto.LoginResponseDTO;
import edu.eci.dosw.techcup.service.AuthService;
import edu.eci.dosw.techcup.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * RF-02: Login con JWT | RF-03: Logout
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login con JWT y logout")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, 
                         JwtService jwtService,
                         AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @GetMapping("/csrf")
    @Operation(summary = "Obtener token CSRF", description = "Retorna el token CSRF usado en solicitudes POST/PUT/DELETE")
    public ResponseEntity<Map<String, String>> csrf(CsrfToken token) {
        return ResponseEntity.ok(Map.of("token", token.getToken()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "RF-02: Autentica con correo y contraseña, retorna JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "RF-03: Cierra la sesión del usuario")
    @ApiResponse(responseCode = "200", description = "Logout exitoso")
    public ResponseEntity<String> logout(@RequestParam String email) {
        authService.logout(email);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}