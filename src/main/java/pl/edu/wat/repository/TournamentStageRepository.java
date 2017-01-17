package pl.edu.wat.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.service.dto.TournamentStageDTO;

import java.util.List;

public interface TournamentStageRepository extends JpaRepository<TournamentStage,Long> {
    List<TournamentStage> findByTournamentId(Long id);

    @Query("select ts from TournamentStage ts where ts.tournamentMatch is not null and ts.tournament.isDeleted = :isDeleted order by ts.id desc")
    List<TournamentStage> findLastMatches(boolean isDeleted, Pageable pageable);

}
