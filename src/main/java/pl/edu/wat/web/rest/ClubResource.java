package pl.edu.wat.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.wat.service.ClubService;
import pl.edu.wat.web.rest.util.HeaderUtil;
import pl.edu.wat.web.rest.util.PaginationUtil;
import pl.edu.wat.service.dto.ClubDTO;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Club.
 */
@RestController
@RequestMapping("/api")
public class ClubResource {

    private final Logger log = LoggerFactory.getLogger(ClubResource.class);
        
    @Inject
    private ClubService clubService;

    /**
     * POST  /clubs : Create a new club.
     *
     * @param clubDTO the clubDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clubDTO, or with status 400 (Bad Request) if the club has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/clubs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClubDTO> createClub(@Valid @RequestBody ClubDTO clubDTO) throws URISyntaxException {
        log.debug("REST request to save Club : {}", clubDTO);
        if (clubDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("club", "idexists", "A new club cannot already have an ID")).body(null);
        }
        ClubDTO result = clubService.save(clubDTO);
        return ResponseEntity.created(new URI("/api/clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("club", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clubs : Updates an existing club.
     *
     * @param clubDTO the clubDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clubDTO,
     * or with status 400 (Bad Request) if the clubDTO is not valid,
     * or with status 500 (Internal Server Error) if the clubDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/clubs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClubDTO> updateClub(@Valid @RequestBody ClubDTO clubDTO) throws URISyntaxException {
        log.debug("REST request to update Club : {}", clubDTO);
        if (clubDTO.getId() == null) {
            return createClub(clubDTO);
        }
        ClubDTO result = clubService.save(clubDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("club", clubDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clubs : get all the clubs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clubs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/clubs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ClubDTO>> getAllClubs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Clubs");
        Page<ClubDTO> page = clubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clubs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clubs/:id : get the "id" club.
     *
     * @param id the id of the clubDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clubDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/clubs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClubDTO> getClub(@PathVariable Long id) {
        log.debug("REST request to get Club : {}", id);
        ClubDTO clubDTO = clubService.findOne(id);
        return Optional.ofNullable(clubDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clubs/:id : delete the "id" club.
     *
     * @param id the id of the clubDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/clubs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        log.debug("REST request to delete Club : {}", id);
        clubService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("club", id.toString())).build();
    }

}
