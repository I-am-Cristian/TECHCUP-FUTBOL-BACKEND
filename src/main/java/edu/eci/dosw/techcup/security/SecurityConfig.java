package edu.eci.dosw.techcup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF desactivado para pruebas (Reactivarlo en producción)
            .csrf(csrf -> csrf.disable())
            
            // Headers de seguridad
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
                .contentSecurityPolicy("default-src 'self'; script-src 'self'; object-src 'none'; base-uri 'self';")
                .and()
                .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block"))
            )
            
            .authorizeHttpRequests(auth -> auth
                // Endpoints PÚBLICOS (sin autenticación)
                .requestMatchers("/api/users/register", "/api/auth/login", "/api/auth/logout").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Torneos - GET requiere autenticación
                .requestMatchers(HttpMethod.GET, "/api/tournaments/**").authenticated()
                
                // Torneos - POST, PUT, DELETE requieren roles específicos
                .requestMatchers(HttpMethod.POST, "/api/tournaments/**").hasAnyRole("ADMINISTRADOR", "ORGANIZADOR")
                .requestMatchers(HttpMethod.PUT, "/api/tournaments/**").hasAnyRole("ADMINISTRADOR", "ORGANIZADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/tournaments/**").hasAnyRole("ADMINISTRADOR", "ORGANIZADOR")
                
                // Usuarios - solo ADMINISTRADOR
                .requestMatchers("/api/users/**").hasRole("ADMINISTRADOR")
                
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Sesión stateless (sin estado, para JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Agregar filtro JWT antes del filtro de autenticación por defecto
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}