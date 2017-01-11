package pl.edu.wat.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.wat.domain.Round;
import pl.edu.wat.service.RoundService;
import pl.edu.wat.web.rest.util.HeaderUtil;
import pl.edu.wat.web.rest.util.PaginationUtil;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Round.
 */
@RestController
@RequestMapping("/api")
public class RoundResource {

    private final Logger log = LoggerFactory.getLogger(RoundResource.class);

    @Inject
    private RoundService roundService;

    /**
     * POST  /rounds : Create a new round.
     *
     * @param round the round to create
     * @return the ResponseEntity with status 201 (Created) and with body the new round, or with status 400 (Bad Request) if the round has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rounds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Round> createRound(@RequestBody Round round) throws URISyntaxException {
        log.debug("REST request to save Round : {}", round);
        if (round.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("round", "idexists", "A new round cannot already have an ID")).body(null);
        }
        Round result = roundService.save(round);
        return ResponseEntity.created(new URI("/api/rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("round", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rounds : Updates an existing round.
     *
     * @param round the round to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated round,
     * or with status 400 (Bad Request) if the round is not valid,
     * or with status 500 (Internal Server Error) if the round couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rounds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Round> updateRound(@RequestBody Round round) throws URISyntaxException {
        log.debug("REST request to update Round : {}", round);
        if (round.getId() == null) {
            return createRound(round);
        }
        Round result = roundService.save(round);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("round", round.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rounds : get all the rounds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rounds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rounds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Round>> getAllRounds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Rounds");
        Page<Round> page = roundService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rounds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rounds/:id : get the "id" round.
     *
     * @param id the id of the round to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the round, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rounds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Round>> getRound(@PathVariable Long id) {
        log.debug("REST request to get Round : {}", id);
        List<Round> rounds = roundService.findForLeague(id);
        return Optional.ofNullable(rounds)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rounds/:id : delete the "id" round.
     *
     * @param id the id of the round to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rounds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        log.debug("REST request to delete Round : {}", id);
        roundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("round", id.toString())).build();
    }

}
