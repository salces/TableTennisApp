package pl.edu.wat.service.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.domain.Tournament;
import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.service.dto.TournamentStageDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTournamentStageMapper {

    public TournamentStageDTO toTournamentStageDTO(TournamentStage tournamentStage) {
        TournamentStageDTO tournamentStageDTO = TournamentStageDTO.builder()
            .id(tournamentStage.getId())
            .phase(tournamentStage.getPhase())
            .phaseCode(tournamentStage.getPhaseCode())
            .tournamentId(tournamentStage.getTournament().getId())
            .tournamentName(tournamentStage.getTournament().getName())
            .build();

        if (tournamentStage.getWinner() != null) {
            tournamentStageDTO.setWinnerId(tournamentStage.getWinner().getId());
        }
        if (tournamentStage.getNextStage() != null) {
            tournamentStageDTO.setNextStageId(tournamentStage.getNextStage().getId());
        }
        if (tournamentStage.getTournamentMatch() != null) {
            tournamentStageDTO.setFirstPlayerScore(tournamentStage.getTournamentMatch().getFirstPlayerScore());
            tournamentStageDTO.setSecondPlayerScore(tournamentStage.getTournamentMatch().getSecondPlayerScore());
            tournamentStageDTO.setTournamentId(tournamentStage.getTournamentMatch().getId());
        }
        if(tournamentStage.getFirstPlayer() != null){
            tournamentStageDTO.setFirstPlayerId(tournamentStage.getFirstPlayer().getId());
            tournamentStageDTO.setFirstPlayerName(tournamentStage.getFirstPlayer().getName());
            tournamentStageDTO.setFirstPlayerSurname(tournamentStage.getFirstPlayer().getSurname());
        }
        if(tournamentStage.getSecondPlayer() != null){
            tournamentStageDTO.setSecondPlayerId(tournamentStage.getSecondPlayer().getId());
            tournamentStageDTO.setSecondPlayerName(tournamentStage.getSecondPlayer().getName());
            tournamentStageDTO.setSecondPlayerSurname(tournamentStage.getSecondPlayer().getSurname());
        }


        return tournamentStageDTO;
    }

    public List<TournamentStageDTO> toTournamentStageDTOs(List<TournamentStage> tournamentStages) {
        List<TournamentStageDTO> tournamentStageDTOs = new ArrayList<>();
        tournamentStages.forEach(ts -> tournamentStageDTOs.add(toTournamentStageDTO(ts)));
        return tournamentStageDTOs;
    }
}
