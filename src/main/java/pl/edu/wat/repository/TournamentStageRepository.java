package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.domain.TournamentStage;

import java.util.List;

public interface TournamentStageRepository extends JpaRepository<TournamentStage,Long> {
    List<TournamentStage> findByTournamentId(Long id);
}
