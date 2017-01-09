package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.domain.Tournament;


/**
 * Spring Data JPA repository for the Tournament entity.
 */
@SuppressWarnings("unused")
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

}
