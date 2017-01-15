package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.Image;
import pl.edu.wat.domain.Player;
import pl.edu.wat.domain.Tournament;
import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.repository.ImageRepository;
import pl.edu.wat.repository.PlayerRepository;
import pl.edu.wat.repository.TournamentRepository;
import pl.edu.wat.repository.TournamentStageRepository;
import pl.edu.wat.service.dto.TournamentDTO;
import pl.edu.wat.service.dto.TournamentStageDTO;
import pl.edu.wat.service.mapper.CustomTournamentStageMapper;
import pl.edu.wat.service.mapper.TournamentMapper;
import pl.edu.wat.service.mapper.TournamentStageMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentService.class);

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentMapper tournamentMapper;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private ImageRepository imageRepository;

    @Inject
    private TournamentStageRepository tournamentStageRepository;

    @Inject
    private CustomTournamentStageMapper customTournamentStageMapper;

    public TournamentDTO save(TournamentDTO tournamentDTO) {
        log.debug("Request to save Tournament : {}", tournamentDTO);
        if (!isModelCorrect(tournamentDTO)) {
            throw new RuntimeException();
        }

        Tournament tournament = createTournament(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        TournamentDTO result = tournamentMapper.tournamentToTournamentDTO(tournament);
        return result;
    }

    /**
     * Get all the tournaments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TournamentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tournaments");
        Page<Tournament> result = tournamentRepository.findAll(pageable);
        return result.map(tournament -> tournamentMapper.tournamentToTournamentDTO(tournament));
    }

    /**
     * Get one tournament by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TournamentDTO findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        Tournament tournament = tournamentRepository.findOne(id);
        TournamentDTO tournamentDTO = tournamentMapper.tournamentToTournamentDTO(tournament);
        return tournamentDTO;
    }

    /**
     * Delete the  tournament by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.delete(id);
    }


    private Tournament createTournament(TournamentDTO tournamentDTO) {
        List<Player> players = new ArrayList<>();
        tournamentDTO.getChosenPlayers()
            .forEach(id ->
                players.add(playerRepository.findOne(id))
            );
        Collections.shuffle(players);

        Image image = null;
        if (tournamentDTO.getImageId() != null)
            imageRepository.findOne(tournamentDTO.getImageId());

        Tournament tournament = new Tournament(tournamentDTO.getName(), image);

        int phase = tournamentDTO.getPhase();

        for (int i = 0; i < phase; i++) {
            TournamentStage stage = new TournamentStage(phase);
            stage.setFirstPlayer(players.get(i));
            stage.setSecondPlayer(players.get(i + phase));
            stage.setTournament(tournament);
            TournamentStage savedStage = tournamentStageRepository.save(stage);
            tournament.addStage(savedStage);
        }
        return tournament;
    }

    private boolean isModelCorrect(TournamentDTO tournamentDTO) {
        if (tournamentDTO.getPhase() * 2 != tournamentDTO.getChosenPlayers().size()) {
            return false;
        }

        if (tournamentDTO.getName() == null || tournamentDTO.getName().equals("")) {
            return false;
        }
        return true;
    }

    public List<TournamentStageDTO> getLastMatches() {
        return customTournamentStageMapper.toTournamentStageDTOs(tournamentStageRepository.findLastMatches(new PageRequest(0, 5)));
    }
}
