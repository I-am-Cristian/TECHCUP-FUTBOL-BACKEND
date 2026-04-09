package edu.eci.dosw.techcup.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "initial_date")
    private LocalDate initialDate;

    @Column(name = "final_date")
    private LocalDate finalDate;

    @Column(name = "teams_number")
    private int teamsNumber;

    @Column(name = "inscription_cost")
    private double inscriptionCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TournamentState state;

    public Tournament() {}

    public Tournament(Long id, String name) {
        this.id = id;
        this.name = name;
        this.state = TournamentState.DRAFT;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getInitialDate() { return initialDate; }
    public void setInitialDate(LocalDate initialDate) { this.initialDate = initialDate; }

    public LocalDate getFinalDate() { return finalDate; }
    public void setFinalDate(LocalDate finalDate) { this.finalDate = finalDate; }

    public int getTeamsNumber() { return teamsNumber; }
    public void setTeamsNumber(int teamsNumber) { this.teamsNumber = teamsNumber; }

    public double getInscriptionCost() { return inscriptionCost; }
    public void setInscriptionCost(double inscriptionCost) { this.inscriptionCost = inscriptionCost; }

    public TournamentState getState() { return state; }
    public void setState(TournamentState state) { this.state = state; }
}