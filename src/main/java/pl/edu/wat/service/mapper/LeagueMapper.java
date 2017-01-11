package pl.edu.wat.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.League;
import pl.edu.wat.domain.Round;
import pl.edu.wat.service.dto.LeagueDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LeagueMapper {

    @Transactional
    public LeagueDTO fromLeagueToLeagueDTO(League league){
        Set<Round> rounds = new HashSet<>();
        rounds.addAll(league.getRounds());
        return LeagueDTO.builder()
            .id(league.getId())
            .image(league.getImage())
            .name(league.getName())
//            .rounds(rounds)
            .build();
    }

    @Transactional
    public List<LeagueDTO> fromLeaguesToLeagueDTOs(List<League> leagues){
        List<LeagueDTO> leagueDTOs = new ArrayList<>();
        leagues.
            forEach(l -> leagueDTOs.add(fromLeagueToLeagueDTO(l)));

        return leagueDTOs;
    }
}
