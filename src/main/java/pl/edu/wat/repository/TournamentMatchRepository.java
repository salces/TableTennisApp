package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.domain.TournamentStage;

import java.util.List;

public interface TournamentMatchRepository extends JpaRepository<TournamentMatch,Long> {

    List<TournamentMatch> findAllByTournamentStage(TournamentStage tournamentStage);
}
