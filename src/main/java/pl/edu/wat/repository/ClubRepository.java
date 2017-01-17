package pl.edu.wat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wat.domain.Club;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Club entity.
 */
@SuppressWarnings("unused")
public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query("select club from Club club where club.manager.login = ?#{principal.username}")
    List<Club> findByManagerIsCurrentUser();

    List<Club> findAllByIsDeleted(boolean isDeleted);
    Page<Club> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

}
