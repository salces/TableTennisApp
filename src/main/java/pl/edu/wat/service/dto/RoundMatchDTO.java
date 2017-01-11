package pl.edu.wat.service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RoundMatchDTO {
    private int id;
    private int firstClubScore;
    private int secondClubScore;
}
