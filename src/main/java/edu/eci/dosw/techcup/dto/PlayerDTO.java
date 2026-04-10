package edu.eci.dosw.techcup.dto;

import edu.eci.dosw.techcup.entity.MemberState;
import edu.eci.dosw.techcup.entity.ParticipantType;
import edu.eci.dosw.techcup.entity.Position;
import edu.eci.dosw.techcup.entity.UserRole;

public class PlayerDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Integer dorsal;
    private Position position;
    private ParticipantType participantType;
    private Integer semester;
    private boolean available;
    private UserRole role;
    private MemberState state;

    public PlayerDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDorsal() { return dorsal; }
    public void setDorsal(Integer dorsal) { this.dorsal = dorsal; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public ParticipantType getParticipantType() { return participantType; }
    public void setParticipantType(ParticipantType participantType) { this.participantType = participantType; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public MemberState getState() { return state; }
    public void setState(MemberState state) { this.state = state; }
}