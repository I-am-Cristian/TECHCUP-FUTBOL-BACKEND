package edu.eci.dosw.techcup.entity;

import java.time.LocalDate;

public class Tournament {
    //Attributes
    private long id;
    private String name;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private int teamsNumber;
    private double inscriptionCost; //inscription cost for a team
    private TournamentState state;

    public Tournament(long id, String name) {
        this.id = id;
        this.name = name;
        state = TournamentState.DRAFT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public int getTeamsNumber() {
        return teamsNumber;
    }

    public void setTeamsNumber(int teamsNumber) {
        this.teamsNumber = teamsNumber;
    }

    public double getInscriptionCost() {
        return inscriptionCost;
    }

    public void setInscriptionCost(double inscriptionCost) {
        this.inscriptionCost = inscriptionCost;
    }

    public TournamentState getState() {
        return state;
    }

    public void setState(TournamentState state) {
        this.state = state;
    }
}
