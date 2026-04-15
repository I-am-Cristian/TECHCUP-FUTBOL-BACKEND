package edu.eci.dosw.techcup.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad para generar hashes BCrypt de contraseñas.
 * 
 * IMPORTANTE: Las contraseñas ya están siendo encriptadas automáticamente
 * en el constructor de UserService. Este archivo es solo de referencia.
 * 
 * Usuarios precargados en el sistema:
 * 1. admin@escuelaing.edu.co / admin123 (ROL: ADMINISTRADOR)
 * 2. jugador@gmail.com / jugador123 (ROL: JUGADOR)
 * 
 * PRUEBAS EN POSTMAN:
 * 
 * 1. Obtener token JWT:
 *    POST http://localhost:8080/api/auth/login
 *    Headers: Content-Type: application/json
 *    Body (raw JSON):
 *    {
 *      "email": "admin@escuelaing.edu.co",
 *      "password": "admin123"
 *    }
 *    
 *    Respuesta esperada (200 OK):
 *    {
 *      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlc2N1ZWxhaW5nLmVkdS5jbyIsImlhdCI6MTcwNjU2NzgwMCwiZXhwIjoxNzA2NjU0MjAwfQ...."
 *    }
 * 
 * 2. Usar el token para acceder a endpoints protegidos:
 *    GET http://localhost:8080/api/users
 *    Headers: 
 *      Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlc2N1ZWxhaW5nLmVkdS5jbyIsImlhdCI6MTcwNjU2NzgwMCwiZXhwIjoxNzA2NjU0MjAwfQ....
 *    
 *    Respuesta esperada (200 OK): Lista de usuarios en JSON
 * 
 * 3. Intentar acceder sin token:
 *    GET http://localhost:8080/api/users
 *    (Sin header Authorization)
 *    
 *    Respuesta esperada (401 Unauthorized)
 * 
 * 4. Intentar login con credenciales incorrectas:
 *    POST http://localhost:8080/api/auth/login
 *    Body:
 *    {
 *      "email": "admin@escuelaing.edu.co",
 *      "password": "wrongpassword"
 *    }
 *    
 *    Respuesta esperada (401 Unauthorized)
 */
public class BCryptUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("=== Generador de Hashes BCrypt ===\n");
        
        String password1 = "admin123";
        String password2 = "jugador123";
        
        System.out.println("Contraseña: " + password1);
        System.out.println("Hash BCrypt: " + encoder.encode(password1));
        System.out.println();
        
        System.out.println("Contraseña: " + password2);
        System.out.println("Hash BCrypt: " + encoder.encode(password2));
        System.out.println();
        
        System.out.println("Nota: Cada vez que ejecutes este programa, los hashes serán diferentes");
        System.out.println("debido al salt aleatorio de BCrypt, pero todos son válidos.");
    }
}
