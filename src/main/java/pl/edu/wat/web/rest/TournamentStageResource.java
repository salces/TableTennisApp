package pl.edu.wat.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.service.TournamentStageService;
import pl.edu.wat.web.rest.util.HeaderUtil;
import pl.edu.wat.web.rest.util.PaginationUtil;
import pl.edu.wat.service.dto.TournamentStageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing TournamentStage.
 */
@RestController
@RequestMapping("/api")
public class TournamentStageResource {

    private final Logger log = LoggerFactory.getLogger(TournamentStageResource.class);

    @Inject
    private TournamentStageService tournamentStageService;

    /**
     * POST  /tournament-stages : Create a new tournamentStage.
     *
     * @param tournamentStageDTO the tournamentStageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tournamentStageDTO, or with status 400 (Bad Request) if the tournamentStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournament-stages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<TournamentStageDTO> createTournamentStage(@RequestBody TournamentStageDTO tournamentStageDTO) throws URISyntaxException {
        log.debug("REST request to save TournamentStage : {}", tournamentStageDTO);
//        if (tournamentStageDTO.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tournamentStage", "idexists", "A new tournamentStage cannot already have an ID")).body(null);
//        }
        TournamentStageDTO result = tournamentStageService.proceedNextStage(tournamentStageDTO);
        return ResponseEntity.created(new URI("/api/tournament-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tournamentStage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tournament-stages : Updates an existing tournamentStage.
     *
     * @param tournamentStageDTO the tournamentStageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tournamentStageDTO,
     * or with status 400 (Bad Request) if the tournamentStageDTO is not valid,
     * or with status 500 (Internal Server Error) if the tournamentStageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournament-stages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TournamentStageDTO> updateTournamentStage(@RequestBody TournamentStageDTO tournamentStageDTO) throws URISyntaxException {
        log.debug("REST request to update TournamentStage : {}", tournamentStageDTO);
        if (tournamentStageDTO.getId() == null) {
            return createTournamentStage(tournamentStageDTO);
        }
        TournamentStageDTO result = tournamentStageService.save(tournamentStageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tournamentStage", tournamentStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tournament-stages : get all the tournamentStages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tournamentStages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tournament-stages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TournamentStageDTO>> getAllTournamentStages(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TournamentStages");
        Page<TournamentStageDTO> page = tournamentStageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tournament-stages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tournament-stages/:id : get the "id" tournamentStage.
     *
     * @param id the id of the tournamentStageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tournamentStageDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tournament-stages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TournamentStageDTO>> getStagesForTournament(@PathVariable Long id) {
        log.debug("REST request to get TournamentStage : {}", id);
        List<TournamentStageDTO> tournamentStageDTO = tournamentStageService.getForTournament(id);
        tournamentStageDTO.forEach(t -> System.out.println(t.getFirstPlayerName()));
        return Optional.ofNullable(tournamentStageDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tournament-stages/:id : delete the "id" tournamentStage.
     *
     * @param id the id of the tournamentStageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tournament-stages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTournamentStage(@PathVariable Long id) {
        log.debug("REST request to delete TournamentStage : {}", id);
        tournamentStageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tournamentStage", id.toString())).build();
    }

}
