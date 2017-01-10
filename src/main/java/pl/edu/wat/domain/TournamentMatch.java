package pl.edu.wat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TournamentMatch.
 */
@Entity
@Table(name = "tournament_match")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_player_score", nullable = false)
    private int firstPlayerScore;

    @NotNull
    @Column(name = "second_player_score", nullable = false)
    private int secondPlayerScore;

    @OneToOne(mappedBy = "tournamentMatch")
    @JsonIgnore
    private TournamentStage tournamentStage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public TournamentMatch firstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        return this;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public TournamentMatch secondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
        return this;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public TournamentStage getTournamentStage() {
        return tournamentStage;
    }

    public TournamentMatch tournamentStage(TournamentStage tournamentStage) {
        this.tournamentStage = tournamentStage;
        return this;
    }

    public void setTournamentStage(TournamentStage tournamentStage) {
        this.tournamentStage = tournamentStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TournamentMatch tournamentMatch = (TournamentMatch) o;
        if(tournamentMatch.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tournamentMatch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TournamentMatch{" +
            "id=" + id +
            ", firstPlayerScore='" + firstPlayerScore + "'" +
            ", secondPlayerScore='" + secondPlayerScore + "'" +
            '}';
    }
}
