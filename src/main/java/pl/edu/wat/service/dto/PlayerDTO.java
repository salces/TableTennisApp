package pl.edu.wat.service.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    @Size(min = 2, max = 25)
    private String surname;

    @NotNull
    @Size(min = 2, max = 25)
    private String nationality;

    @NotNull
    @Min(value = 140)
    @Max(value = 240)
    private Integer height;

    private Long managerId;

    private String managerLogin;

    private Long clubId;

    private String clubPrefix;

    private Long imageId;

    private String imageAlias;
}
