package pl.edu.wat.service.dto;

import lombok.*;
import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.Image;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDTO {
    private Long id;
    private String name;
    private Image image;
    private List<Club> competitors;
}
