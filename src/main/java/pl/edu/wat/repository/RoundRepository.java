package pl.edu.wat.repository;

import pl.edu.wat.domain.Round;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Round entity.
 */
@SuppressWarnings("unused")
public interface RoundRepository extends JpaRepository<Round,Long> {

    List<Round> findByLeagueIdOrderByOrdinalAscHaAsc(Long id);
}
