package pl.edu.wat.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TournamentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    private Long imageId;

    private int phase;

    private List<Long> chosenPlayers;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public List<Long> getChosenPlayers() {
        return chosenPlayers;
    }

    public void setChosenPlayers(List<Long> chosenPlayers) {
        this.chosenPlayers = chosenPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TournamentDTO tournamentDTO = (TournamentDTO) o;

        if ( ! Objects.equals(id, tournamentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TournamentDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", playersIds='" + chosenPlayers + "'" +
            '}';
    }
}
