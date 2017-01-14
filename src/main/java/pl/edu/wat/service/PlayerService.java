package pl.edu.wat.service;

import pl.edu.wat.domain.Player;
import pl.edu.wat.repository.PlayerRepository;
import pl.edu.wat.service.dto.PlayerDTO;
import pl.edu.wat.service.mapper.PlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Player.
 */
@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerMapper playerMapper;

    /**
     * Save a player.
     *
     * @param playerDTO the entity to save
     * @return the persisted entity
     */
    public PlayerDTO save(PlayerDTO playerDTO) {
        log.debug("Request to save Player : {}", playerDTO);
        Player player = playerMapper.playerDTOToPlayer(playerDTO);
        player = playerRepository.save(player);
        PlayerDTO result = playerMapper.playerToPlayerDTO(player);
        return result;
    }

    /**
     *  Get all the players.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        Page<Player> result = playerRepository.findAll(pageable);
        return result.map(player -> playerMapper.playerToPlayerDTO(player));
    }

    /**
     *  Get one player by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PlayerDTO findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        Player player = playerRepository.findOne(id);
        PlayerDTO playerDTO = playerMapper.playerToPlayerDTO(player);
        return playerDTO;
    }

    /**
     *  Delete the  player by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.delete(id);
    }


    public Player getRandom() {
        List<Player> players = playerRepository.findAll();
        Random random = new Random();
        return players.get(random.nextInt(players.size()));
    }
}
