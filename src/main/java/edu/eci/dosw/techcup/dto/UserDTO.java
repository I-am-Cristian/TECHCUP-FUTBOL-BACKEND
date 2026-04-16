package edu.eci.dosw.techcup.dto;

import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.UserRole;

public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private MemberState state;

    public UserDTO() {}

    public UserDTO(Long id, String email, UserRole role, MemberState state) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.state = state;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public MemberState getState() { return state; }
    public void setState(MemberState state) { this.state = state; }
}