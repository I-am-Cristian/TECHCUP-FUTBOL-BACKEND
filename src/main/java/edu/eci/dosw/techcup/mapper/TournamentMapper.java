package edu.eci.dosw.techcup.mapper;

import edu.eci.dosw.techcup.dto.TournamentDTO;
import edu.eci.dosw.techcup.entity.Tournament;
import edu.eci.dosw.techcup.entity.TournamentState;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper {

    public TournamentDTO toDto(Tournament tournament) {
        if (tournament == null) return null;
        TournamentDTO dto = new TournamentDTO();
        dto.setId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setInitialDate(tournament.getInitialDate());
        dto.setFinalDate(tournament.getFinalDate());
        dto.setState(tournament.getState());
        dto.setInscriptionCost(tournament.getInscriptionCost());
        dto.setTeamsNumber(tournament.getTeamsNumber());
        return dto;
    }

    public Tournament toEntity(TournamentDTO dto) {
        if (dto == null) return null;
        Tournament tournament = new Tournament();
        tournament.setName(dto.getName());
        tournament.setInitialDate(dto.getInitialDate());
        tournament.setFinalDate(dto.getFinalDate());
        tournament.setState(dto.getState() != null ? dto.getState() : TournamentState.DRAFT);
        if (dto.getInscriptionCost() != null)
            tournament.setInscriptionCost(dto.getInscriptionCost());
        if (dto.getTeamsNumber() != null)
            tournament.setTeamsNumber(dto.getTeamsNumber());
        return tournament;
    }
}