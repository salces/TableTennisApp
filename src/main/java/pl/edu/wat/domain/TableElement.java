package pl.edu.wat.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TableElement {
    private Club club;
    private int matchesPlayed;
    private int points;
    private int wins;
    private int defeats;
    private int littleWins;
    private int littleDefeats;

}
