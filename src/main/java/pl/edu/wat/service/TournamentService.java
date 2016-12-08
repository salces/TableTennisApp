package pl.edu.wat.service;

import pl.edu.wat.domain.Tournament;
import pl.edu.wat.repository.TournamentRepository;
import pl.edu.wat.service.dto.TournamentDTO;
import pl.edu.wat.service.mapper.TournamentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tournament.
 */
@Service
@Transactional
public class TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentService.class);
    
    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentMapper tournamentMapper;

    /**
     * Save a tournament.
     *
     * @param tournamentDTO the entity to save
     * @return the persisted entity
     */
    public TournamentDTO save(TournamentDTO tournamentDTO) {
        log.debug("Request to save Tournament : {}", tournamentDTO);
        Tournament tournament = tournamentMapper.tournamentDTOToTournament(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        TournamentDTO result = tournamentMapper.tournamentToTournamentDTO(tournament);
        return result;
    }

    /**
     *  Get all the tournaments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TournamentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tournaments");
        Page<Tournament> result = tournamentRepository.findAll(pageable);
        return result.map(tournament -> tournamentMapper.tournamentToTournamentDTO(tournament));
    }

    /**
     *  Get one tournament by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TournamentDTO findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        Tournament tournament = tournamentRepository.findOne(id);
        TournamentDTO tournamentDTO = tournamentMapper.tournamentToTournamentDTO(tournament);
        return tournamentDTO;
    }

    /**
     *  Delete the  tournament by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.delete(id);
    }
}
