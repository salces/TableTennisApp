package pl.edu.wat.service;

import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.repository.TournamentRepository;
import pl.edu.wat.repository.TournamentStageRepository;
import pl.edu.wat.service.dto.TournamentStageDTO;
import pl.edu.wat.service.mapper.TournamentStageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TournamentStage.
 */
@Service
@Transactional
public class TournamentStageService {

    private final Logger log = LoggerFactory.getLogger(TournamentStageService.class);

    @Inject
    private TournamentStageRepository tournamentStageRepository;

    @Inject
    private TournamentStageMapper tournamentStageMapper;

    @Inject
    private TournamentRepository tournamentRepository;

    /**
     * Save a tournamentStage.
     *
     * @param tournamentStageDTO the entity to save
     * @return the persisted entity
     */
    public TournamentStageDTO save(TournamentStageDTO tournamentStageDTO) {
        log.debug("Request to save TournamentStage : {}", tournamentStageDTO);
        TournamentStage tournamentStage = tournamentStageMapper.tournamentStageDTOToTournamentStage(tournamentStageDTO);
        tournamentStage = tournamentStageRepository.save(tournamentStage);
        TournamentStageDTO result = tournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage);
        return result;
    }

    /**
     *  Get all the tournamentStages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TournamentStageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TournamentStages");
        Page<TournamentStage> result = tournamentStageRepository.findAll(pageable);
        return result.map(tournamentStage -> tournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage));
    }

    /**
     *  Get one tournamentStage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TournamentStageDTO findOne(Long id) {
        log.debug("Request to get TournamentStage : {}", id);
        TournamentStage tournamentStage = tournamentStageRepository.findOne(id);
        TournamentStageDTO tournamentStageDTO = tournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage);
        return tournamentStageDTO;
    }

    /**
     *  Delete the  tournamentStage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TournamentStage : {}", id);
        tournamentStageRepository.delete(id);
    }

    public Set<TournamentStage> findAllForTournament(Long ID){
        return tournamentRepository.findOne(ID).getStages();
    }
}
