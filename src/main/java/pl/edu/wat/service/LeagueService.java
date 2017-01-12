package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.*;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.repository.LeagueRepository;
import pl.edu.wat.repository.RoundRepository;
import pl.edu.wat.service.dto.LeagueDTO;

import javax.inject.Inject;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

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

    public League createLeague2(LeagueDTO leagueDTO) {
        League league = new League();
        league.setCompetitors(fromListToSet(leagueDTO.getCompetitors()));
        league.setName(leagueDTO.getName());
        league.image(leagueDTO.getImage());

        List<Club> competitors = leagueDTO.getCompetitors();
        List<Round> rounds = new ArrayList<>();

        for (int i = 0; i < competitors.size() - 1; i++) {
            for (int j = 0; j < competitors.size() / 2; j++) {
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

    private List<Club> roundRobin(List<Club> competitors) {
        Club c = competitors.get(competitors.size() - 1);
        competitors.remove(competitors.size() - 1);
        competitors.add(2, c);
        return competitors;
    }

    @Transactional
    public List<TableElement> getTable(Long id) {
        List<TableElement> tableElements = new ArrayList<>();

        League league = leagueRepository.findOne(id);
        league
            .getCompetitors()
            .forEach(c -> tableElements.add(getTableElement(c, league.getRounds())));
        return sort(tableElements);
    }

    private List<TableElement> sort(List<TableElement> tableElements) {
        return tableElements
            .stream()
            .sorted((te1,te2) -> compare(te1,te2))
            .collect(Collectors.toList());
    }

    private int compare(TableElement te1, TableElement te2) {
        if(te1.getPoints() > te2.getPoints()){
            return -1;
        } else if(te1.getPoints() < te2.getPoints()){
            return 1;
        } else {
            if(te1.getSmallWins() > te2.getSmallWins()){
                return -1;
            } else if(te1.getSmallWins() < te2.getSmallWins()){
                return 1;
            } else {
                if(te1.getSmallDefeats() < te2.getSmallDefeats()){
                    return -1;
                } else if(te1.getSmallDefeats() > te2.getSmallDefeats()){
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }


    private TableElement getTableElement(Club club, Set<Round> rounds) {
        List<Round> leagueRounds = fromSetToList(rounds);

        List<Round> firstTeamRounds = leagueRounds
            .stream()
            .filter(r -> r.getFirstTeam().getId() == club.getId())
            .collect(Collectors.toList());

        List<Round> secondTeamRounds = leagueRounds
            .stream()
            .filter(r -> r.getSecondTeam().getId() == club.getId())
            .collect(Collectors.toList());

        return sumUp(getTableElementForFirstTeam(club, firstTeamRounds), getTableElementForSecondTeam(club, secondTeamRounds));
    }


    private TableElement sumUp(TableElement tableElementForFirstTeam, TableElement tableElementForSecondTeam) {
        TableElement tableElement = TableElement.builder()
            .club(tableElementForFirstTeam.getClub())
            .points(tableElementForFirstTeam.getPoints() + tableElementForSecondTeam.getPoints())
            .wins(tableElementForFirstTeam.getWins() + tableElementForSecondTeam.getPoints())
            .defeats(tableElementForFirstTeam.getDefeats() + tableElementForSecondTeam.getDefeats())
            .matchesPlayed(tableElementForFirstTeam.getMatchesPlayed() + tableElementForSecondTeam.getMatchesPlayed())
            .smallWins(tableElementForFirstTeam.getSmallWins() + tableElementForSecondTeam.getSmallWins())
            .smallDefeats(tableElementForFirstTeam.getSmallDefeats() + tableElementForSecondTeam.getSmallDefeats())
            .build();
        return tableElement;
    }

    private TableElement getTableElementForFirstTeam(Club club, List<Round> firstTeamRounds) {
        TableElement tableElement = new TableElement();
        tableElement.setClub(club);

        final int pointsForWin = 3;
        final int pointsForDraw = 1;

        int matchesPlayed = 0;
        int points = 0;
        int defeats = 0;
        int wins = 0;
        int smallWins = 0;
        int smallDefeats = 0;

        for (Round r : firstTeamRounds) {
            if (r.getTournamentMatch() != null) {
                if (r.getTournamentMatch().getFirstPlayerScore() > r.getTournamentMatch().getSecondPlayerScore()) {
                    wins++;
                    points += pointsForWin;
                } else {
                    defeats++;
                }
                smallWins += r.getTournamentMatch().getFirstPlayerScore();
                smallDefeats += r.getTournamentMatch().getSecondPlayerScore();
                matchesPlayed++;
            }
        }

        tableElement.setMatchesPlayed(matchesPlayed);
        tableElement.setPoints(points);
        tableElement.setWins(wins);
        tableElement.setDefeats(defeats);
        tableElement.setSmallDefeats(smallDefeats);
        tableElement.setSmallWins(smallWins);

        return tableElement;
    }

    private TableElement getTableElementForSecondTeam(Club club, List<Round> secondTeamRounds) {
        TableElement tableElement = new TableElement();
        tableElement.setClub(club);

        final int pointsForWin = 3;

        int matchesPlayed = 0;
        int points = 0;
        int defeats = 0;
        int wins = 0;
        int smallWins = 0;
        int smallDefeats = 0;

        for (Round r : secondTeamRounds) {
            if (r.getTournamentMatch() != null) {

                if (r.getTournamentMatch().getFirstPlayerScore() < r.getTournamentMatch().getSecondPlayerScore()) {
                    wins++;
                    points += pointsForWin;
                } else {
                    defeats++;
                }
                smallWins += r.getTournamentMatch().getSecondPlayerScore();
                smallDefeats += r.getTournamentMatch().getFirstPlayerScore();
                matchesPlayed++;
            }

        }

        tableElement.setMatchesPlayed(matchesPlayed);
        tableElement.setPoints(points);
        tableElement.setWins(wins);
        tableElement.setDefeats(defeats);
        tableElement.setSmallDefeats(smallDefeats);
        tableElement.setSmallWins(smallWins);

        return tableElement;
    }

    private List<Round> fromSetToList(Set<Round> set) {
        List<Round> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }
}
