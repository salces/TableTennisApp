package pl.edu.wat.repository;

import pl.edu.wat.domain.TournamentStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TournamentStage entity.
 */
@SuppressWarnings("unused")
public interface TournamentStageRepository extends JpaRepository<TournamentStage,Long> {

}
