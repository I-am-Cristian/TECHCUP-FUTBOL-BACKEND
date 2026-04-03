package edu.eci.dosw.techcup.entity;

public abstract class User implements IAuthenticable {

    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private MemberState state;

    public User() {}

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = UserRole.JUGADOR;
        this.state = MemberState.ACTIVE;
    }

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