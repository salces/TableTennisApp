package pl.edu.wat.repository;

import pl.edu.wat.domain.Image;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
public interface ImageRepository extends JpaRepository<Image,Long> {

    @Query("select image from Image image where image.owner.login = ?#{principal.username}")
    List<Image> findByOwnerIsCurrentUser();

}
