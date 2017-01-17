package pl.edu.wat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.domain.League;
import pl.edu.wat.domain.Tournament;

import java.util.List;


/**
 * Spring Data JPA repository for the Tournament entity.
 */
@SuppressWarnings("unused")
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    List<Tournament> findAllByIsDeleted(boolean isDeleted);
    Page<Tournament> findAllByIsDeleted(boolean isDeleted, Pageable pageable);



}
