package pl.edu.wat.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pl.edu.wat.domain.Round;

import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round,Long> {

    List<Round> findByLeagueIdOrderByOrdinalAscHaAsc(Long id);

    @Query("select round from Round round where round.tournamentMatch is not null and round.league.isDeleted = :isDeleted order by round.id desc")
    List<Round> findLastRounds(@Param("isDeleted") boolean isDeleted, Pageable pageable);
}
