package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.Player;
import pl.edu.wat.repository.PlayerRepository;
import pl.edu.wat.service.dto.PlayerDTO;
import pl.edu.wat.service.mapper.PlayerMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerMapper playerMapper;

    @Inject
    private LoggedUserService loggedUserService;

    public PlayerDTO save(PlayerDTO playerDTO) {
        log.debug("Request to save Player : {}", playerDTO);
        Player player = playerMapper.playerDTOToPlayer(playerDTO);
        player.setManager(loggedUserService.getLoggedUser());
        player = playerRepository.save(player);
        PlayerDTO result = playerMapper.playerToPlayerDTO(player);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<PlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        Page<Player> result = playerRepository.findAllByIsDeleted(false, pageable);
        return result.map(player -> playerMapper.playerToPlayerDTO(player));
    }

    @Transactional(readOnly = true)
    public PlayerDTO findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        Player player = playerRepository.findOne(id);
        PlayerDTO playerDTO = playerMapper.playerToPlayerDTO(player);
        return playerDTO;
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        Player player = playerRepository.findOne(id);
        player.setDeleted(true);
        playerRepository.save(player);
    }

    public Player getRandom() {
        List<Player> players = playerRepository.findAllByIsDeleted(false);
        Random random = new Random();
        return players.get(random.nextInt(players.size()));
    }
}
