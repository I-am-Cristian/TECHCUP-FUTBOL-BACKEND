package edu.eci.dosw.techcup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player extends User {

    // id heredado de User — no se redeclara

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int dorsal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "participant_type", nullable = false, length = 30)
    private ParticipantType participantType;

    @Column(nullable = false)
    private int semester;

    @Column(nullable = false)
    private boolean available;

    public Player() {}

    public Player(Long id, String name, int dorsal, Position position,
                  ParticipantType participantType) {
        this.setId(id);
        this.name = name;
        this.dorsal = dorsal;
        this.position = position;
        this.participantType = participantType;
        this.available = false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public ParticipantType getParticipantType() { return participantType; }
    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}