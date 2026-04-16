package edu.eci.dosw.techcup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "managers")
public class Manager extends User {

    public Manager() {}

    public Manager(Long id, String email, String password) {
        super(id, email, password);
    }
}