package pl.edu.wat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.wat.domain.League;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("select distinct league from League league left join fetch league.competitors")
    List<League> findAllWithEagerRelationships();

    @Query("select league from League league left join fetch league.competitors where league.id =:id")
    League findOneWithEagerRelationships(@Param("id") Long id);

    List<League> findAllByIsDeleted(boolean isDeleted);
    Page<League> findAllByIsDeleted(boolean isDeleted, Pageable pageable);


}
