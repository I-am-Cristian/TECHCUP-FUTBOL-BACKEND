package edu.eci.dosw.techcup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ¿Para qué sirve la anotación @EnableWebSecurity?
 * Habilita la configuración de seguridad web de Spring Security en la aplicación.
 * Permite personalizar la configuración de seguridad mediante beans y sobreescribir
 * el comportamiento por defecto de Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * ¿Qué es y para qué sirve AuthenticationManager?
     * Es el componente central de Spring Security que coordina el proceso de autenticación.
     * Recibe las credenciales del usuario, las valida contra el UserDetailsService y
     * retorna un objeto Authentication si las credenciales son válidas.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * ¿Qué es y para qué sirve PasswordEncoder?
     * Es una interfaz que define cómo se codifican (encriptan) las contraseñas.
     * Proporciona métodos para encriptar contraseñas y verificar si una contraseña
     * en texto plano coincide con una contraseña encriptada.
     * 
     * ¿Qué es y para qué sirve BCryptPasswordEncoder?
     * Es una implementación de PasswordEncoder que usa el algoritmo BCrypt para
     * encriptar contraseñas. BCrypt es un algoritmo de hash adaptativo que incluye
     * un salt aleatorio y es resistente a ataques de fuerza bruta.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/auth/logout").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
