package pl.edu.wat.service.mapper;

import pl.edu.wat.domain.*;
import pl.edu.wat.service.dto.TournamentStageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TournamentStage and its DTO TournamentStageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TournamentStageMapper {

    @Mapping(source = "firstPlayer.id", target = "firstPlayerId")
    @Mapping(source = "secondPlayer.id", target = "secondPlayerId")
    @Mapping(source = "winner.id", target = "winnerId")
    @Mapping(source = "nextStage.id", target = "nextStageId")
    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournamentMatch.id", target = "tournamentMatchId")
    @Mapping(source = "tournamentMatch.firstPlayerScore", target = "firstPlayerScore")
    @Mapping(source = "tournamentMatch.secondPlayerScore", target = "secondPlayerScore")
    TournamentStageDTO tournamentStageToTournamentStageDTO(TournamentStage tournamentStage);

    List<TournamentStageDTO> tournamentStagesToTournamentStageDTOs(List<TournamentStage> tournamentStages);

    @Mapping(source = "firstPlayerId", target = "firstPlayer")
    @Mapping(source = "secondPlayerId", target = "secondPlayer")
    @Mapping(source = "winnerId", target = "winner")
    @Mapping(source = "nextStageId", target = "nextStage")
    @Mapping(source = "tournamentId", target = "tournament")
    @Mapping(source = "tournamentMatchId", target = "tournamentMatch")
    TournamentStage tournamentStageDTOToTournamentStage(TournamentStageDTO tournamentStageDTO);

    List<TournamentStage> tournamentStageDTOsToTournamentStages(List<TournamentStageDTO> tournamentStageDTOs);

    default Player playerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return player;
    }

    default TournamentStage tournamentStageFromId(Long id) {
        if (id == null) {
            return null;
        }
        TournamentStage tournamentStage = new TournamentStage();
        tournamentStage.setId(id);
        return tournamentStage;
    }

    default Tournament tournamentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tournament tournament = new Tournament();
        tournament.setId(id);
        return tournament;
    }

    default TournamentMatch tournamentMatchFromId(Long id) {
        if (id == null) {
            return null;
        }
        TournamentMatch tournamentMatch = new TournamentMatch();
        tournamentMatch.setId(id);
        return tournamentMatch;
    }
}
