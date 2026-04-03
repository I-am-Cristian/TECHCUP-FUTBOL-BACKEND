package edu.eci.dosw.techcup.entity;

public class Player {

    private Long id;
    private String name;
    private int dorsal;
    private Position position;
    private ParticipantType participantType;
    private int semester;
    private boolean available;

    public Player() {}

    public Player(Long id, String name, int dorsal, Position position,
                  ParticipantType participantType) {
        this.id = id;
        this.name = name;
        this.dorsal = dorsal;
        this.position = position;
        this.participantType = participantType;
        this.available = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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