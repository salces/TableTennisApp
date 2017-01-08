package pl.edu.wat.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.edu.wat.domain.enumeration.TournamentPhase;

/**
 * A TournamentStage.
 */
@Entity
@Table(name = "tournament_stage")
@NoArgsConstructor
public class TournamentStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private TournamentPhase phase;

    @Column(name = "phase_code")
    private Integer phaseCode;

    @ManyToOne
    private Player firstPlayer;

    @ManyToOne
    private Player secondPlayer;

    @ManyToOne
    private Player winner;

    @ManyToOne
    private TournamentStage nextStage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID")
    private Tournament tournament;

    @OneToOne
    @JoinColumn(unique = true)
    private TournamentMatch tournamentMatch;

    public TournamentStage(int phase){
        this.phaseCode = phase;
        this.phase = TournamentPhase.fromInt(phase);
    }

    public TournamentStage(TournamentPhase phase){
        this.phase = phase;
        this.phaseCode = phase.getLvl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TournamentPhase getPhase() {
        return phase;
    }

    public TournamentStage phase(TournamentPhase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(TournamentPhase phase) {
        this.phase = phase;
    }

    public Integer getPhaseCode() {
        return phaseCode;
    }

    public TournamentStage phaseCode(Integer phaseCode) {
        this.phaseCode = phaseCode;
        return this;
    }

    public void setPhaseCode(Integer phaseCode) {
        this.phaseCode = phaseCode;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public TournamentStage firstPlayer(Player player) {
        this.firstPlayer = player;
        return this;
    }

    public void setFirstPlayer(Player player) {
        this.firstPlayer = player;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public TournamentStage secondPlayer(Player player) {
        this.secondPlayer = player;
        return this;
    }

    public void setSecondPlayer(Player player) {
        this.secondPlayer = player;
    }

    public Player getWinner() {
        return winner;
    }

    public TournamentStage winner(Player player) {
        this.winner = player;
        return this;
    }

    public void setWinner(Player player) {
        this.winner = player;
    }

    public TournamentStage getNextStage() {
        return nextStage;
    }

    public TournamentStage nextStage(TournamentStage tournamentStage) {
        this.nextStage = tournamentStage;
        return this;
    }

    public void setNextStage(TournamentStage tournamentStage) {
        this.nextStage = tournamentStage;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TournamentStage tournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TournamentMatch getTournamentMatch() {
        return tournamentMatch;
    }

    public TournamentStage tournamentMatch(TournamentMatch tournamentMatch) {
        this.tournamentMatch = tournamentMatch;
        return this;
    }

    public void setTournamentMatch(TournamentMatch tournamentMatch) {
        this.tournamentMatch = tournamentMatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TournamentStage tournamentStage = (TournamentStage) o;
        if(tournamentStage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tournamentStage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TournamentStage{" +
            "id=" + id +
            ", phase='" + phase + "'" +
            ", phaseCode='" + phaseCode + "'" +
            '}';
    }
}
