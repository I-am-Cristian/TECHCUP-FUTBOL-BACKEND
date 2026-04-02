package edu.dosw.techcup.model.user;

public abstract class User {

    // Attributes
    private long id;
    private String email;
    private String password;
    private MemberState state;

    // Constructor
    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.state = MemberState.ACTIVE;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MemberState getState() {
        return state;
    }

    public void setState(MemberState state) {
        this.state = state;
    }
}
