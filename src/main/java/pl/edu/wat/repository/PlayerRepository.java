package pl.edu.wat.repository;

import pl.edu.wat.domain.Player;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("select player from Player player where player.manager.login = ?#{principal.username}")
    List<Player> findByManagerIsCurrentUser();

}
