package pl.edu.wat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "prefix", length = 25, nullable = false)
    private String prefix;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "location", length = 25, nullable = false)
    private String location;

    @NotNull
    @Min(value = 1750)
    @Max(value = 2500)
    @Column(name = "estabilished", nullable = false)
    private Integer estabilished;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @Size(min = 10, max = 50)
    @Column(name = "home_page", length = 50, nullable = false)
    private String homePage;

    @ManyToOne
    private User manager;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private Set<Player> players = new HashSet<>();

    @ManyToOne
    private Image image;

    @ManyToMany(mappedBy = "competitors")
    @JsonIgnore
    private Set<League> leagues = new HashSet<>();

    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public Club prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLocation() {
        return location;
    }

    public Club location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getEstabilished() {
        return estabilished;
    }

    public Club estabilished(Integer estabilished) {
        this.estabilished = estabilished;
        return this;
    }

    public void setEstabilished(Integer estabilished) {
        this.estabilished = estabilished;
    }

    public String getEmail() {
        return email;
    }

    public Club email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePage() {
        return homePage;
    }

    public Club homePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public User getManager() {
        return manager;
    }

    public Club manager(User user) {
        this.manager = user;
        return this;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Club players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Club addPlayers(Player player) {
        players.add(player);
        player.setClub(this);
        return this;
    }

    public Club removePlayers(Player player) {
        players.remove(player);
        player.setClub(null);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Image getImage() {
        return image;
    }

    public Club image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<League> getLeagues() {
        return leagues;
    }

    public Club leagues(Set<League> leagues) {
        this.leagues = leagues;
        return this;
    }

    public Club addLeagues(League league) {
        leagues.add(league);
        league.getCompetitors().add(this);
        return this;
    }

    public Club removeLeagues(League league) {
        leagues.remove(league);
        league.getCompetitors().remove(this);
        return this;
    }

    public void setLeagues(Set<League> leagues) {
        this.leagues = leagues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Club club = (Club) o;
        if(club.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, club.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Club{" +
            "id=" + id +
            ", prefix='" + prefix + "'" +
            ", location='" + location + "'" +
            ", estabilished='" + estabilished + "'" +
            ", email='" + email + "'" +
            ", homePage='" + homePage + "'" +
            '}';
    }
}
