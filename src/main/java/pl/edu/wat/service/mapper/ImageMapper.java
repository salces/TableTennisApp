package pl.edu.wat.service.mapper;

import pl.edu.wat.domain.*;
import pl.edu.wat.service.dto.ImageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ImageMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    ImageDTO imageToImageDTO(Image image);

    List<ImageDTO> imagesToImageDTOs(List<Image> images);

    @Mapping(source = "ownerId", target = "owner")
    Image imageDTOToImage(ImageDTO imageDTO);

    List<Image> imageDTOsToImages(List<ImageDTO> imageDTOs);
}
