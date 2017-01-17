package pl.edu.wat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.domain.Player;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("select player from Player player where player.manager.login = ?#{principal.username}")
    List<Player> findByManagerIsCurrentUser();

    List<Player> findAllByIsDeleted(boolean isDeleted);
    Page<Player> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

}
