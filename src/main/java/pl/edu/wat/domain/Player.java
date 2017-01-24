package pl.edu.wat.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "player")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "surname", length = 25, nullable = false)
    private String surname;

    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "nationality", length = 25, nullable = false)
    private String nationality;

    @NotNull
    @Min(value = 140)
    @Max(value = 240)
    @Column(name = "height", nullable = false)
    private Integer height;

    @ManyToOne
    private User manager;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Image image;

    private boolean isDeleted = false;
}
