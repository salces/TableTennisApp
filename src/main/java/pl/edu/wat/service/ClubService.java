package pl.edu.wat.service;

import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.Player;
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
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubService.class);

    @Inject
    private ClubRepository clubRepository;

    @Inject
    private ClubMapper clubMapper;

    @Inject
    private LoggedUserService loggedUserService;

    public ClubDTO save(ClubDTO clubDTO) {
        log.debug("Request to save Club : {}", clubDTO);
        Club club = clubMapper.clubDTOToClub(clubDTO);
        club.setManager(loggedUserService.getLoggedUser());
        club = clubRepository.save(club);
        ClubDTO result = clubMapper.clubToClubDTO(club);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ClubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clubs");
        Page<Club> result = clubRepository.findAllByIsDeleted(false,pageable);
        return result.map(club -> clubMapper.clubToClubDTO(club));
    }


    @Transactional(readOnly = true)
    public ClubDTO findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        Club club = clubRepository.findOne(id);
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);
        return clubDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        Club club = clubRepository.findOne(id);
        club.setDeleted(true);
        clubRepository.save(club);
    }

    public Club getRandom() {
        log.debug("Request to get random Club");
        List<Club> clubs = clubRepository.findAllByIsDeleted(false);
        Random random = new Random();
        return clubs.get(random.nextInt(clubs.size()));
    }
}
