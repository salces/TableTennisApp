package pl.edu.wat.repository;

import pl.edu.wat.domain.TournamentMatch;

import org.springframework.data.jpa.repository.*;
import pl.edu.wat.domain.TournamentStage;

import java.util.List;

/**
 * Spring Data JPA repository for the TournamentMatch entity.
 */
@SuppressWarnings("unused")
public interface TournamentMatchRepository extends JpaRepository<TournamentMatch,Long> {

    List<TournamentMatch> findAllByTournamentStage(TournamentStage tournamentStage);
}
