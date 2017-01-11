package pl.edu.wat.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Round.
 */
@Entity
@Table(name = "round")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int  ordinal;

    private int ha;

    @ManyToOne
    private Club firstTeam;

    @ManyToOne
    private Club secondTeam;

    @ManyToOne
    private TournamentMatch tournamentMatch;

    @ManyToOne
//    @JsonIgnore
    private League league;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getHa() {
        return ha;
    }

    public void setHa(int ha) {
        this.ha = ha;
    }

    public Club getFirstTeam() {
        return firstTeam;
    }

    public Round firstTeam(Club club) {
        this.firstTeam = club;
        return this;
    }

    public void setFirstTeam(Club club) {
        this.firstTeam = club;
    }

    public Club getSecondTeam() {
        return secondTeam;
    }

    public Round secondTeam(Club club) {
        this.secondTeam = club;
        return this;
    }

    public void setSecondTeam(Club club) {
        this.secondTeam = club;
    }

    public TournamentMatch getTournamentMatch() {
        return tournamentMatch;
    }

    public Round tournamentMatch(TournamentMatch tournamentMatch) {
        this.tournamentMatch = tournamentMatch;
        return this;
    }

    public void setTournamentMatch(TournamentMatch tournamentMatch) {
        this.tournamentMatch = tournamentMatch;
    }

    public League getLeague() {
        return league;
    }

    public Round league(League league) {
        this.league = league;
        return this;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Round round = (Round) o;
        if(round.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, round.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Round{" +
            "id=" + id +
            '}';
    }
}
