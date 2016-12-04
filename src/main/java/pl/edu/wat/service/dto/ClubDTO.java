package pl.edu.wat.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Club entity.
 */
public class ClubDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    private String prefix;

    @NotNull
    @Size(min = 2, max = 25)
    private String location;

    @NotNull
    @Min(value = 1750)
    @Max(value = 2500)
    private Integer estabilished;

    @NotNull
    @Size(min = 6, max = 50)
    private String email;

    @NotNull
    @Size(min = 10, max = 50)
    private String homePage;


    private Long managerId;
    

    private String managerLogin;

    private Long imageId;
    

    private String imageAlias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getEstabilished() {
        return estabilished;
    }

    public void setEstabilished(Integer estabilished) {
        this.estabilished = estabilished;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long userId) {
        this.managerId = userId;
    }


    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String userLogin) {
        this.managerLogin = userLogin;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }


    public String getImageAlias() {
        return imageAlias;
    }

    public void setImageAlias(String imageAlias) {
        this.imageAlias = imageAlias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClubDTO clubDTO = (ClubDTO) o;

        if ( ! Objects.equals(id, clubDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClubDTO{" +
            "id=" + id +
            ", prefix='" + prefix + "'" +
            ", location='" + location + "'" +
            ", estabilished='" + estabilished + "'" +
            ", email='" + email + "'" +
            ", homePage='" + homePage + "'" +
            '}';
    }
}
