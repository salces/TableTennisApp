package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.Tournament;
import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.domain.enumeration.TournamentPhase;
import pl.edu.wat.repository.TournamentMatchRepository;
import pl.edu.wat.repository.TournamentRepository;
import pl.edu.wat.repository.TournamentStageRepository;
import pl.edu.wat.service.dto.TournamentStageDTO;
import pl.edu.wat.service.mapper.TournamentStageMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TournamentStageService {

    private final Logger log = LoggerFactory.getLogger(TournamentStageService.class);

    @Inject
    private TournamentStageRepository tournamentStageRepository;

    @Inject
    private TournamentStageMapper tournamentStageMapper;

    @Inject
    private TournamentMatchRepository tournamentMatchRepository;

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentStageMapper customTournamentStageMapper;

    public TournamentStageDTO save(TournamentStageDTO tournamentStageDTO) {
        log.debug("Request to save TournamentStage : {}", tournamentStageDTO);
        TournamentStage tournamentStage = tournamentStageMapper.tournamentStageDTOToTournamentStage(tournamentStageDTO);
        tournamentStage = tournamentStageRepository.save(tournamentStage);
        TournamentStageDTO result = customTournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage);
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
        return result.map(tournamentStage -> customTournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage));
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
        TournamentStageDTO tournamentStageDTO = customTournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage);
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

    public TournamentStageDTO proceedNextStage(TournamentStageDTO tournamentStageDTO){
        TournamentStageDTO proceededStage = null;

        if(isFinalStage(tournamentStageDTO)){
            proceededStage = proceedFinalStage(tournamentStageDTO);
        } else
        if(doesAlreadyExist(tournamentStageDTO)){
            proceededStage = proceedExistingTournament(tournamentStageDTO);
        } else {
            proceededStage = proceedNewNextStage(tournamentStageDTO);
        }
        return proceededStage;
    }

//    @Transactional
    private TournamentStageDTO proceedFinalStage(TournamentStageDTO tournamentStageDTO) {
        TournamentStage currentTournamentStage =
            tournamentStageRepository.findOne(tournamentStageDTO.getCurrentStageId());
        TournamentMatch tournamentMatch = TournamentMatch.builder()
            .tournamentStage(currentTournamentStage)
            .firstPlayerScore(tournamentStageDTO.getFirstPlayerScore())
            .secondPlayerScore(tournamentStageDTO.getSecondPlayerScore())
            .build();
        currentTournamentStage.setTournamentMatch(tournamentMatch);

        tournamentMatchRepository.save(tournamentMatch);
        tournamentStageRepository.save(currentTournamentStage);
        return customTournamentStageMapper.tournamentStageToTournamentStageDTO(currentTournamentStage);
    }

//    @Transactional
    private boolean isFinalStage(TournamentStageDTO tournamentStageDTO) {
        TournamentStage currentTournamentStage =
            tournamentStageRepository.findOne(tournamentStageDTO.getCurrentStageId());
        if(currentTournamentStage.getPhase() == TournamentPhase.FINAL){
            return true;
        } else {
            return false;
        }
    }

//    @Transactional
    private TournamentStageDTO proceedNewNextStage(TournamentStageDTO tournamentStageDTO){
        TournamentStage currentTournamentStage =
            tournamentStageRepository.findOne(tournamentStageDTO.getCurrentStageId());
        TournamentMatch tournamentMatch = TournamentMatch.builder()
            .tournamentStage(currentTournamentStage)
            .firstPlayerScore(tournamentStageDTO.getFirstPlayerScore())
            .secondPlayerScore(tournamentStageDTO.getSecondPlayerScore())
            .build();
        currentTournamentStage.setTournamentMatch(tournamentMatch);
        if(tournamentStageDTO.getFirstPlayerScore() > tournamentStageDTO.getSecondPlayerScore()){
            currentTournamentStage.setWinner(currentTournamentStage.getFirstPlayer());
        } else {
            currentTournamentStage.setWinner(currentTournamentStage.getSecondPlayer());
        }

        tournamentMatchRepository.save(tournamentMatch);

        TournamentStage nextTournamentStage = new TournamentStage(currentTournamentStage.getPhase().getNextPhase());
        nextTournamentStage.setFirstPlayer(currentTournamentStage.getWinner());
        nextTournamentStage.setTournament(currentTournamentStage.getTournament());

        nextTournamentStage = tournamentStageRepository.save(nextTournamentStage);
        currentTournamentStage.setNextStage(nextTournamentStage);
        tournamentStageRepository.save(currentTournamentStage);

        return customTournamentStageMapper.tournamentStageToTournamentStageDTO(nextTournamentStage);
    }

//    @Transactional
    private TournamentStageDTO proceedExistingTournament(TournamentStageDTO tournamentStageDTO){
        TournamentStage currentTournamentStage =
            tournamentStageRepository.findOne(tournamentStageDTO.getCurrentStageId());
        Tournament currentTournament = currentTournamentStage.getTournament();
        TournamentMatch tournamentMatch = TournamentMatch.builder()
            .tournamentStage(currentTournamentStage)
            .firstPlayerScore(tournamentStageDTO.getFirstPlayerScore())
            .secondPlayerScore(tournamentStageDTO.getSecondPlayerScore())
            .build();
        currentTournamentStage.setTournamentMatch(tournamentMatch);
        if(tournamentStageDTO.getFirstPlayerScore() > tournamentStageDTO.getSecondPlayerScore()){
            currentTournamentStage.setWinner(currentTournamentStage.getFirstPlayer());
        } else {
            currentTournamentStage.setWinner(currentTournamentStage.getSecondPlayer());
        }

        tournamentMatchRepository.save(tournamentMatch);

        List<TournamentStage> currentTournamentStages = tournamentStageRepository
            .findByTournamentId(currentTournamentStage.getTournament().getId());
        TournamentStage existingStage = currentTournamentStages
            .stream()
            .filter(s ->
                s.getPhase() == currentTournamentStage.getPhase().getNextPhase()
                    && (s.getFirstPlayer() == null
                    || s.getSecondPlayer() == null))
            .findAny()
            .get();


        if(existingStage.getFirstPlayer() == null){
            existingStage.setFirstPlayer(currentTournamentStage.getWinner());
        } else {
            existingStage.setSecondPlayer(currentTournamentStage.getWinner());
        }

        existingStage = tournamentStageRepository.save(existingStage);
        currentTournamentStage.setNextStage(existingStage);
        tournamentStageRepository.save(currentTournamentStage);

        return customTournamentStageMapper.tournamentStageToTournamentStageDTO(existingStage);
    }

//    @Transactional
    private boolean doesAlreadyExist(TournamentStageDTO tournamentStageDTO){
        TournamentStage currentTournamentStage =
            tournamentStageRepository.findOne(tournamentStageDTO.getCurrentStageId());
        List<TournamentStage> currentTournamentStages = tournamentStageRepository
                        .findByTournamentId(currentTournamentStage.getTournament().getId());
        return currentTournamentStages
            .stream()
            .anyMatch(s ->
                s.getPhase() == currentTournamentStage.getPhase().getNextPhase()
                    && (s.getFirstPlayer() == null
                    || s.getSecondPlayer() == null));
    }

    @Transactional
    public List<TournamentStageDTO> getForTournament(Long id){
        List<TournamentStage> tournamentStages = new ArrayList<>();
        tournamentStages.addAll(tournamentRepository.findOne(id).getStages());
        return customTournamentStageMapper.tournamentStagesToTournamentStageDTOs(tournamentStages);

    }

}
