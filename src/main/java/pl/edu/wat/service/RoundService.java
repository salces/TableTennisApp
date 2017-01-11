package pl.edu.wat.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.edu.wat.domain.Round;
import pl.edu.wat.domain.Tournament;
import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.repository.RoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.wat.repository.TournamentMatchRepository;
import pl.edu.wat.service.dto.RoundMatchDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Round.
 */
@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundService.class);

    @Inject
    private RoundRepository roundRepository;

    @Inject
    private TournamentMatchRepository tournamentMatchRepository;
    /**
     * Save a round.
     *
     * @param round the entity to save
     * @return the persisted entity
     */
    public Round save(Round round) {
        log.debug("Request to save Round : {}", round);
        Round result = roundRepository.save(round);
        return result;
    }

    /**
     *  Get all the rounds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Round> findAll(Pageable pageable) {
        log.debug("Request to get all Rounds");
        Page<Round> result = roundRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one round by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Round findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        Round round = roundRepository.findOne(id);
        return round;
    }

    /**
     *  Delete the  round by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.delete(id);
    }

    public List<Round> findForLeague(Long id) {
        return roundRepository.findByLeagueIdOrderByOrdinalAscHaAsc(id);
    }

    public void addMatch(RoundMatchDTO roundMatchDTO) {
        Round round = roundRepository.findOne(new Long(roundMatchDTO.getId()));
        TournamentMatch match = TournamentMatch.builder()
            .firstPlayerScore(roundMatchDTO.getFirstClubScore())
            .secondPlayerScore(roundMatchDTO.getSecondClubScore())
            .build();
        match = tournamentMatchRepository.save(match);
        round.setTournamentMatch(match);
        roundRepository.save(round);
    }
}
