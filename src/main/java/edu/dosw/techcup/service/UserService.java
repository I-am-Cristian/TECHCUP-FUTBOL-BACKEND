package edu.dosw.techcup.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.dosw.techcup.model.user.Manager;
import edu.dosw.techcup.model.user.MemberState;
import edu.dosw.techcup.model.user.User;

public class UserService {

    // RF-01 y RF-04: dominios de correo permitidos
    private static final String ECI_DOMAIN = "@escuelaing.edu.co";
    private static final String GMAIL_DOMAIN = "@gmail.com";

    // Attributes
    private final Map<Long, User> users = new LinkedHashMap<>();

    

    /**
     * Registra un nuevo usuario si el correo tiene dominio válido y no está registrado.
     * Por defecto se crea como Manager (rol base); en sprints futuros se distinguirá por rol.
     *
     * @param id       identificador único del usuario
     * @param email    correo institucional (@escuelaing.edu.co) o personal (@gmail.com)
     * @param password contraseña del usuario
     * @return el User creado
     * @throws IllegalArgumentException si el dominio no está permitido o el correo ya existe
     */
    public User registerUser(long id, String email, String password) {
        if (!isValidEmailDomain(email)) {
            throw new IllegalArgumentException("Dominio de correo no permitido: " + email);
        }
        if (emailAlreadyExists(email)) {
            throw new IllegalArgumentException("El correo ya está registrado: " + email);
        }
        User newUser = new Manager(id, email, password);
        users.put(id, newUser);
        return newUser;
    }


    /**
     * Retorna todos los usuarios registrados en el sistema.
     */
    public Collection<User> getUsers() {
        return users.values();
    }

    /**
     * Retorna un usuario por su id, o null si no existe.
     */
    public User getUserById(long id) {
        return users.getOrDefault(id, null);
    }

    /**
     * Retorna un usuario por su correo, o null si no existe.
     */
    public User getUserByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Inactiva un usuario cambiando su estado a UNACTIVE.
     *
     * @param id identificador del usuario
     * @throws IllegalArgumentException si el usuario no existe
     */
    public void inactivateUser(long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
        user.setState(MemberState.UNACTIVE);
    }

    /**
     * Suspende un usuario cambiando su estado a SUSPENDED.
     *
     * @param id identificador del usuario
     * @throws IllegalArgumentException si el usuario no existe
     */
    public void suspendUser(long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
        user.setState(MemberState.SUSPENDED);
    }

    /**
     * Reactiva un usuario cambiando su estado a ACTIVE.
     *
     * @param id identificador del usuario
     * @throws IllegalArgumentException si el usuario no existe
     */
    public void activateUser(long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
        user.setState(MemberState.ACTIVE);
    }


    private boolean isValidEmailDomain(String email) {
        if (email == null) return false;
        return email.endsWith(ECI_DOMAIN) || email.endsWith(GMAIL_DOMAIN);
    }

    private boolean emailAlreadyExists(String email) {
        return users.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}
