package pl.edu.wat.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.domain.Player;
import pl.edu.wat.service.PlayerService;
import pl.edu.wat.service.dto.PlayerDTO;
import pl.edu.wat.web.rest.util.HeaderUtil;
import pl.edu.wat.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlayerResource {

    private final Logger log = LoggerFactory.getLogger(PlayerResource.class);

    @Inject
    private PlayerService playerService;

    @RequestMapping(value = "/players",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) throws URISyntaxException {
        log.debug("REST request to save Player : {}", playerDTO);
        if (playerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("player", "idexists", "A new player cannot already have an ID")).body(null);
        }
        PlayerDTO result = playerService.save(playerDTO);
        return ResponseEntity.created(new URI("/api/players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("player", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/players",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlayerDTO> updatePlayer(@Valid @RequestBody PlayerDTO playerDTO) throws URISyntaxException {
        log.debug("REST request to update Player : {}", playerDTO);
        if (playerDTO.getId() == null) {
            return createPlayer(playerDTO);
        }
        PlayerDTO result = playerService.save(playerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("player", playerDTO.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/players/public",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Players");
        Page<PlayerDTO> page = playerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/players/public/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long id) {
        log.debug("REST request to get Player : {}", id);
        PlayerDTO playerDTO = playerService.findOne(id);
        return Optional.ofNullable(playerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/players/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        log.debug("REST request to delete Player : {}", id);
        playerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("player", id.toString())).build();
    }

    @RequestMapping(value = "/players/public/random",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Player getRandom(){
        return playerService.getRandom();
    }
}
