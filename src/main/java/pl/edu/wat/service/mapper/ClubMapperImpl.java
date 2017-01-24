package pl.edu.wat.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.Image;
import pl.edu.wat.domain.User;
import pl.edu.wat.service.dto.ClubDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class ClubMapperImpl implements ClubMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ClubDTO clubToClubDTO(Club club) {
        if ( club == null ) {
            return null;
        }

        ClubDTO clubDTO = new ClubDTO();

        clubDTO.setImageId( clubImageId( club ) );
        clubDTO.setImageAlias( clubImageAlias( club ) );
        clubDTO.setManagerId( clubManagerId( club ) );
        clubDTO.setManagerLogin( clubManagerLogin( club ) );
        clubDTO.setId( club.getId() );
        clubDTO.setPrefix( club.getPrefix() );
        clubDTO.setLocation( club.getLocation() );
        clubDTO.setEstabilished( club.getEstabilished() );
        clubDTO.setEmail( club.getEmail() );
        clubDTO.setHomePage( club.getHomePage() );

        return clubDTO;
    }

    @Override
    public List<ClubDTO> clubsToClubDTOs(List<Club> clubs) {
        if ( clubs == null ) {
            return null;
        }

        List<ClubDTO> list = new ArrayList<ClubDTO>();
        for ( Club club : clubs ) {
            list.add( clubToClubDTO( club ) );
        }

        return list;
    }

    @Override
    public Club clubDTOToClub(ClubDTO clubDTO) {
        if ( clubDTO == null ) {
            return null;
        }

        Club club = new Club();

        club.setImage( imageFromId( clubDTO.getImageId() ) );
        club.setManager( userMapper.userFromId( clubDTO.getManagerId() ) );
        club.setId( clubDTO.getId() );
        club.setPrefix( clubDTO.getPrefix() );
        club.setLocation( clubDTO.getLocation() );
        club.setEstabilished( clubDTO.getEstabilished() );
        club.setEmail( clubDTO.getEmail() );
        club.setHomePage( clubDTO.getHomePage() );

        return club;
    }

    @Override
    public List<Club> clubDTOsToClubs(List<ClubDTO> clubDTOs) {
        if ( clubDTOs == null ) {
            return null;
        }

        List<Club> list = new ArrayList<Club>();
        for ( ClubDTO clubDTO : clubDTOs ) {
            list.add( clubDTOToClub( clubDTO ) );
        }

        return list;
    }

    private Long clubImageId(Club club) {

        if ( club == null ) {
            return null;
        }
        Image image = club.getImage();
        if ( image == null ) {
            return null;
        }
        Long id = image.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String clubImageAlias(Club club) {

        if ( club == null ) {
            return null;
        }
        Image image = club.getImage();
        if ( image == null ) {
            return null;
        }
        String alias = image.getAlias();
        if ( alias == null ) {
            return null;
        }
        return alias;
    }

    private Long clubManagerId(Club club) {

        if ( club == null ) {
            return null;
        }
        User manager = club.getManager();
        if ( manager == null ) {
            return null;
        }
        Long id = manager.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String clubManagerLogin(Club club) {

        if ( club == null ) {
            return null;
        }
        User manager = club.getManager();
        if ( manager == null ) {
            return null;
        }
        String login = manager.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }
}
