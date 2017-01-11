package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.League;
import pl.edu.wat.domain.Player;
import pl.edu.wat.domain.Round;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.repository.LeagueRepository;
import pl.edu.wat.repository.RoundRepository;
import pl.edu.wat.service.dto.LeagueDTO;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
public class LeagueService {

    private final Logger log = LoggerFactory.getLogger(LeagueService.class);

    @Inject
    private LeagueRepository leagueRepository;

    @Inject
    private RoundRepository roundRepository;

    @Inject
    private ClubRepository clubRepository;

    public League save(League league) {
        log.debug("Request to save League : {}", league);
        League result = leagueRepository.save(league);
        return result;
    }

    /**
     * Get all the leagues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<League> findAll(Pageable pageable) {
        log.debug("Request to get all Leagues");
        Page<League> result = leagueRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one league by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public League findOne(Long id) {
        log.debug("Request to get League : {}", id);
        League league = leagueRepository.findOneWithEagerRelationships(id);
        return league;
    }

    /**
     * Delete the  league by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete League : {}", id);
        leagueRepository.delete(id);
    }

    public League createLeague(LeagueDTO leagueDTO) {
        League league = new League();
        league.setName(leagueDTO.getName());
        league.setCompetitors(fromListToSet(leagueDTO.getCompetitors()));

        List<Round> rounds = getFirstHalf(league, leagueDTO.getCompetitors());
//        rounds.addAll(getSecondHalf(rounds));
        league.setRounds(new HashSet<>(rounds));
        return save(league);
    }

    private List<Round> inverse(List<Round> rounds) {
        List<Round> newRounds = new ArrayList<>();

        rounds
            .forEach(r -> {
                Round round = Round
                    .builder()
                    .league(r.getLeague())
                    .firstTeam(r.getSecondTeam())
                    .secondTeam(r.getFirstTeam())
                    .ordinal(r.getOrdinal())
                    .ha(1)
                    .build();
                newRounds.add(round);
                roundRepository.save(round);
            });
        return newRounds;
    }

    private List<Round> getFirstHalf(League league, List<Club> competitors) {
        List<Round> rounds = new ArrayList<>();
        List<Club> clubs = new ArrayList<>();
        clubs.addAll(competitors);

        clubs
            .forEach(c -> {
                int index = clubs.indexOf(c);

                for (int i = index + 1; i < clubs.size(); i++) {
                    Round r = Round
                        .builder()
                        .firstTeam(c)
                        .secondTeam(clubs.get(i))
                        .league(league)
                        .build();
                    rounds.add(r);
                    roundRepository.save(r);
                }
            });
        return rounds;
    }

    private HashSet fromListToSet(List list) {
        HashSet set = new HashSet();
        set.addAll(list);
        return set;
    }

    public League createLeague2(LeagueDTO leagueDTO){
        League league = new League();
        league.setCompetitors(fromListToSet(leagueDTO.getCompetitors()));
        league.setName(leagueDTO.getName());
        league.image(leagueDTO.getImage());

        List<Club> competitors = leagueDTO.getCompetitors();
        List<Round> rounds = new ArrayList<>();

        for(int i = 0; i < competitors.size() - 1; i++){
            for (int j = 0; j < competitors.size()/2; j++){
                Round r = Round.builder()
                    .firstTeam(competitors.get(j))
                    .secondTeam(competitors.get(competitors.size() - j - 1))
                    .league(league)
                    .ordinal(i)
                    .ha(0)
                    .build();
                r = roundRepository.save(r);
                rounds.add(r);
            }
            competitors = roundRobin(competitors);
        }

        rounds.addAll(inverse(rounds));
        league.setRounds(fromListToSet(rounds));
        league = leagueRepository.save(league);
        return league;
    }

    private List<Club> roundRobin(List<Club> competitors){
        Club c = competitors.get(competitors.size() - 1);
        competitors.remove(competitors.size() - 1);
        competitors.add(2,c);
        return competitors;
    }

}
