package edu.eci.dosw.techcup.dto;

import edu.eci.dosw.techcup.entity.TournamentState;
import java.time.LocalDate;

public class TournamentDTO {
    private Long id;
    private String name;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private TournamentState state;
    private Double inscriptionCost;
    private Integer teamsNumber;

    public TournamentDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getInitialDate() { return initialDate; }
    public void setInitialDate(LocalDate initialDate) { this.initialDate = initialDate; }

    public LocalDate getFinalDate() { return finalDate; }
    public void setFinalDate(LocalDate finalDate) { this.finalDate = finalDate; }

    public TournamentState getState() { return state; }
    public void setState(TournamentState state) { this.state = state; }

    public Double getInscriptionCost() { return inscriptionCost; }
    public void setInscriptionCost(double inscriptionCost) { this.inscriptionCost = inscriptionCost; }

    public Integer getTeamsNumber() { return teamsNumber; }
    public void setTeamsNumber(int teamsNumber) { this.teamsNumber = teamsNumber; }
}