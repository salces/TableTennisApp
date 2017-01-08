package pl.edu.wat.service;

import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.repository.TournamentMatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing TournamentMatch.
 */
@Service
@Transactional
public class TournamentMatchService {

    private final Logger log = LoggerFactory.getLogger(TournamentMatchService.class);
    
    @Inject
    private TournamentMatchRepository tournamentMatchRepository;

    /**
     * Save a tournamentMatch.
     *
     * @param tournamentMatch the entity to save
     * @return the persisted entity
     */
    public TournamentMatch save(TournamentMatch tournamentMatch) {
        log.debug("Request to save TournamentMatch : {}", tournamentMatch);
        TournamentMatch result = tournamentMatchRepository.save(tournamentMatch);
        return result;
    }

    /**
     *  Get all the tournamentMatches.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TournamentMatch> findAll() {
        log.debug("Request to get all TournamentMatches");
        List<TournamentMatch> result = tournamentMatchRepository.findAll();

        return result;
    }


    /**
     *  get all the tournamentMatches where TournamentStage is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TournamentMatch> findAllWhereTournamentStageIsNull() {
        log.debug("Request to get all tournamentMatches where TournamentStage is null");
        return StreamSupport
            .stream(tournamentMatchRepository.findAll().spliterator(), false)
            .filter(tournamentMatch -> tournamentMatch.getTournamentStage() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one tournamentMatch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TournamentMatch findOne(Long id) {
        log.debug("Request to get TournamentMatch : {}", id);
        TournamentMatch tournamentMatch = tournamentMatchRepository.findOne(id);
        return tournamentMatch;
    }

    /**
     *  Delete the  tournamentMatch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TournamentMatch : {}", id);
        tournamentMatchRepository.delete(id);
    }
}
