package edu.eci.dosw.techcup.entity;

import java.time.LocalDateTime;

public class AuditAction {

    //Attributes
    private long id;
    private User user;
    private Action action;
    private LocalDateTime date;
    private String details;

    //Methods
    public AuditAction(long id, User user, Action action) {
        this.id = id;
        this.user = user;
        this.action = action;
        this.date = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Action getAction() {
        return action;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
