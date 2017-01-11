package pl.edu.wat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A League.
 */
@Entity
@Table(name = "league")
public class League implements Serializable {

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

    @ManyToMany
    @JoinTable(name = "league_competitors",
               joinColumns = @JoinColumn(name="leagues_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="competitors_id", referencedColumnName="ID"))
    private Set<Club> competitors = new HashSet<>();

    @OneToMany(mappedBy = "league")
    @JsonIgnore
    private Set<Round> rounds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public League name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public League image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Club> getCompetitors() {
        return competitors;
    }

    public League competitors(Set<Club> clubs) {
        this.competitors = clubs;
        return this;
    }

    public League addCompetitors(Club club) {
        competitors.add(club);
        club.getLeagues().add(this);
        return this;
    }

    public League removeCompetitors(Club club) {
        competitors.remove(club);
        club.getLeagues().remove(this);
        return this;
    }

    public void setCompetitors(Set<Club> clubs) {
        this.competitors = clubs;
    }

    public Set<Round> getRounds() {
        return rounds;
    }

    public League rounds(Set<Round> rounds) {
        this.rounds = rounds;
        return this;
    }

    public League addRounds(Round round) {
        rounds.add(round);
        round.setLeague(this);
        return this;
    }

    public League removeRounds(Round round) {
        rounds.remove(round);
        round.setLeague(null);
        return this;
    }

    public void setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        League league = (League) o;
        if(league.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, league.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "League{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
