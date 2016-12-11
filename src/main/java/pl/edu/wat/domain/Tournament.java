package pl.edu.wat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.wat.domain.exceptions.TooManyPhasesOfSameTypeException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @ManyToOne
    private Image image;

    @OneToMany(mappedBy = "tournament")
//    @JsonIgnore
    private Set<TournamentStage> stages = new HashSet<>();

    public Tournament() {
    }

    public Tournament(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public void addStage(TournamentStage s) throws TooManyPhasesOfSameTypeException {
        checkIfNotTooManySamePhases(s);
        stages.add(s);
    }

    private void checkIfNotTooManySamePhases(TournamentStage stage) throws TooManyPhasesOfSameTypeException {
        long stagesPerPhase = stages.stream()
            .filter(s -> s.getPhase() == stage.getPhase())
            .count();
        if (stagesPerPhase >= stage.getPhaseCode()) {
            throw new TooManyPhasesOfSameTypeException();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tournament name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public Tournament image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<TournamentStage> getStages() {
        return stages;
    }

    public Tournament stages(Set<TournamentStage> tournamentStages) {
        this.stages = tournamentStages;
        return this;
    }

    public Tournament addStages(TournamentStage tournamentStage) {
        stages.add(tournamentStage);
        tournamentStage.setTournament(this);
        return this;
    }

    public Tournament removeStages(TournamentStage tournamentStage) {
        stages.remove(tournamentStage);
        tournamentStage.setTournament(null);
        return this;
    }

    public void setStages(Set<TournamentStage> tournamentStages) {
        this.stages = tournamentStages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        if(tournament.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tournament.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
