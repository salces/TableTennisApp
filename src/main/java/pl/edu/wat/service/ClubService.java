package pl.edu.wat.service;

import pl.edu.wat.domain.Club;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.service.dto.ClubDTO;
import pl.edu.wat.service.mapper.ClubMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Club.
 */
@Service
@Transactional
public class ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubService.class);
    
    @Inject
    private ClubRepository clubRepository;

    @Inject
    private ClubMapper clubMapper;

    /**
     * Save a club.
     *
     * @param clubDTO the entity to save
     * @return the persisted entity
     */
    public ClubDTO save(ClubDTO clubDTO) {
        log.debug("Request to save Club : {}", clubDTO);
        Club club = clubMapper.clubDTOToClub(clubDTO);
        club = clubRepository.save(club);
        ClubDTO result = clubMapper.clubToClubDTO(club);
        return result;
    }

    /**
     *  Get all the clubs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clubs");
        Page<Club> result = clubRepository.findAll(pageable);
        return result.map(club -> clubMapper.clubToClubDTO(club));
    }

    /**
     *  Get one club by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClubDTO findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        Club club = clubRepository.findOne(id);
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);
        return clubDTO;
    }

    /**
     *  Delete the  club by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        clubRepository.delete(id);
    }
}
