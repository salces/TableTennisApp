package pl.edu.wat.repository;

import pl.edu.wat.domain.TournamentMatch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TournamentMatch entity.
 */
@SuppressWarnings("unused")
public interface TournamentMatchRepository extends JpaRepository<TournamentMatch,Long> {

}
