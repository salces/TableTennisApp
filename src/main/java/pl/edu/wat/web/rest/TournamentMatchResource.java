package pl.edu.wat.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.service.TournamentMatchService;
import pl.edu.wat.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing TournamentMatch.
 */
@RestController
@RequestMapping("/api")
public class TournamentMatchResource {

    private final Logger log = LoggerFactory.getLogger(TournamentMatchResource.class);
        
    @Inject
    private TournamentMatchService tournamentMatchService;

    /**
     * POST  /tournament-matches : Create a new tournamentMatch.
     *
     * @param tournamentMatch the tournamentMatch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tournamentMatch, or with status 400 (Bad Request) if the tournamentMatch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournament-matches",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TournamentMatch> createTournamentMatch(@Valid @RequestBody TournamentMatch tournamentMatch) throws URISyntaxException {
        log.debug("REST request to save TournamentMatch : {}", tournamentMatch);
        if (tournamentMatch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tournamentMatch", "idexists", "A new tournamentMatch cannot already have an ID")).body(null);
        }
        TournamentMatch result = tournamentMatchService.save(tournamentMatch);
        return ResponseEntity.created(new URI("/api/tournament-matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tournamentMatch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tournament-matches : Updates an existing tournamentMatch.
     *
     * @param tournamentMatch the tournamentMatch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tournamentMatch,
     * or with status 400 (Bad Request) if the tournamentMatch is not valid,
     * or with status 500 (Internal Server Error) if the tournamentMatch couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournament-matches",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TournamentMatch> updateTournamentMatch(@Valid @RequestBody TournamentMatch tournamentMatch) throws URISyntaxException {
        log.debug("REST request to update TournamentMatch : {}", tournamentMatch);
        if (tournamentMatch.getId() == null) {
            return createTournamentMatch(tournamentMatch);
        }
        TournamentMatch result = tournamentMatchService.save(tournamentMatch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tournamentMatch", tournamentMatch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tournament-matches : get all the tournamentMatches.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of tournamentMatches in body
     */
    @RequestMapping(value = "/tournament-matches",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TournamentMatch> getAllTournamentMatches(@RequestParam(required = false) String filter) {
        if ("tournamentstage-is-null".equals(filter)) {
            log.debug("REST request to get all TournamentMatchs where tournamentStage is null");
            return tournamentMatchService.findAllWhereTournamentStageIsNull();
        }
        log.debug("REST request to get all TournamentMatches");
        return tournamentMatchService.findAll();
    }

    /**
     * GET  /tournament-matches/:id : get the "id" tournamentMatch.
     *
     * @param id the id of the tournamentMatch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tournamentMatch, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tournament-matches/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TournamentMatch> getTournamentMatch(@PathVariable Long id) {
        log.debug("REST request to get TournamentMatch : {}", id);
        TournamentMatch tournamentMatch = tournamentMatchService.findOne(id);
        return Optional.ofNullable(tournamentMatch)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tournament-matches/:id : delete the "id" tournamentMatch.
     *
     * @param id the id of the tournamentMatch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tournament-matches/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTournamentMatch(@PathVariable Long id) {
        log.debug("REST request to delete TournamentMatch : {}", id);
        tournamentMatchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tournamentMatch", id.toString())).build();
    }

}
