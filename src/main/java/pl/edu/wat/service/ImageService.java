package pl.edu.wat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.domain.Image;
import pl.edu.wat.repository.ImageRepository;
import pl.edu.wat.service.dto.ImageDTO;
import pl.edu.wat.service.mapper.ImageMapper;

import javax.inject.Inject;

@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Inject
    private ImageRepository imageRepository;

    @Inject
    private ImageMapper imageMapper;

    @Inject
    private LoggedUserService loggedUserService;

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.imageDTOToImage(imageDTO);
        image.setOwner(loggedUserService.getLoggedUser());
        image = imageRepository.save(image);
        ImageDTO result = imageMapper.imageToImageDTO(image);
        return result;
    }

    /**
     *  Get all the images.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        Page<Image> result = imageRepository.findAllByIsDeleted(false,pageable);
        return result.map(image -> imageMapper.imageToImageDTO(image));
    }

    /**
     *  Get one image by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ImageDTO findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        ImageDTO imageDTO = imageMapper.imageToImageDTO(image);
        return imageDTO;
    }

    /**
     *  Delete the  image by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        Image image = imageRepository.findOne(id);
        image.setDeleted(true);
        imageRepository.save(image);
    }
}
