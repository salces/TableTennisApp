package pl.edu.wat.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wat.domain.Image;
import pl.edu.wat.domain.User;
import pl.edu.wat.service.dto.ImageDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ImageDTO imageToImageDTO(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setOwnerId( imageOwnerId( image ) );
        imageDTO.setOwnerLogin( imageOwnerLogin( image ) );
        imageDTO.setId( image.getId() );
        imageDTO.setAlias( image.getAlias() );
        if ( image.getData() != null ) {
            byte[] data = image.getData();
            imageDTO.setData( Arrays.copyOf( data, data.length ) );
        }
        imageDTO.setDataContentType( image.getDataContentType() );
        imageDTO.setName( image.getName() );

        return imageDTO;
    }

    @Override
    public List<ImageDTO> imagesToImageDTOs(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageDTO> list = new ArrayList<ImageDTO>();
        for ( Image image : images ) {
            list.add( imageToImageDTO( image ) );
        }

        return list;
    }

    @Override
    public Image imageDTOToImage(ImageDTO imageDTO) {
        if ( imageDTO == null ) {
            return null;
        }

        Image image = new Image();

        image.setOwner( userMapper.userFromId( imageDTO.getOwnerId() ) );
        image.setId( imageDTO.getId() );
        image.setAlias( imageDTO.getAlias() );
        if ( imageDTO.getData() != null ) {
            byte[] data = imageDTO.getData();
            image.setData( Arrays.copyOf( data, data.length ) );
        }
        image.setDataContentType( imageDTO.getDataContentType() );
        image.setName( imageDTO.getName() );

        return image;
    }

    @Override
    public List<Image> imageDTOsToImages(List<ImageDTO> imageDTOs) {
        if ( imageDTOs == null ) {
            return null;
        }

        List<Image> list = new ArrayList<Image>();
        for ( ImageDTO imageDTO : imageDTOs ) {
            list.add( imageDTOToImage( imageDTO ) );
        }

        return list;
    }

    private Long imageOwnerId(Image image) {

        if ( image == null ) {
            return null;
        }
        User owner = image.getOwner();
        if ( owner == null ) {
            return null;
        }
        Long id = owner.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String imageOwnerLogin(Image image) {

        if ( image == null ) {
            return null;
        }
        User owner = image.getOwner();
        if ( owner == null ) {
            return null;
        }
        String login = owner.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }
}
