package pl.edu.wat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.domain.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    @Query("select image from Image image where image.owner.login = ?#{principal.username}")
    List<Image> findByOwnerIsCurrentUser();

    List<Image> findAllByIsDeleted(boolean isDeleted);
    Page<Image> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

}
