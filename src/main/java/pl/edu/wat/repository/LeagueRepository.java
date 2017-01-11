package pl.edu.wat.repository;

import pl.edu.wat.domain.League;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the League entity.
 */
@SuppressWarnings("unused")
public interface LeagueRepository extends JpaRepository<League,Long> {

    @Query("select distinct league from League league left join fetch league.competitors")
    List<League> findAllWithEagerRelationships();

    @Query("select league from League league left join fetch league.competitors where league.id =:id")
    League findOneWithEagerRelationships(@Param("id") Long id);

}
