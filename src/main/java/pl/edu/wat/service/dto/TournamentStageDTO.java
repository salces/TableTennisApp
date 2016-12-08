package pl.edu.wat.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pl.edu.wat.domain.enumeration.TournamentPhase;

/**
 * A DTO for the TournamentStage entity.
 */
public class TournamentStageDTO implements Serializable {

    private Long id;

    private TournamentPhase phase;

    private Integer phaseCode;


    private Long firstPlayerId;
    
    private Long secondPlayerId;
    
    private Long winnerId;
    
    private Long nextStageId;
    
    private Long tournamentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public TournamentPhase getPhase() {
        return phase;
    }

    public void setPhase(TournamentPhase phase) {
        this.phase = phase;
    }
    public Integer getPhaseCode() {
        return phaseCode;
    }

    public void setPhaseCode(Integer phaseCode) {
        this.phaseCode = phaseCode;
    }

    public Long getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(Long playerId) {
        this.firstPlayerId = playerId;
    }

    public Long getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(Long playerId) {
        this.secondPlayerId = playerId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long playerId) {
        this.winnerId = playerId;
    }

    public Long getNextStageId() {
        return nextStageId;
    }

    public void setNextStageId(Long tournamentStageId) {
        this.nextStageId = tournamentStageId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TournamentStageDTO tournamentStageDTO = (TournamentStageDTO) o;

        if ( ! Objects.equals(id, tournamentStageDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TournamentStageDTO{" +
            "id=" + id +
            ", phase='" + phase + "'" +
            ", phaseCode='" + phaseCode + "'" +
            '}';
    }
}
