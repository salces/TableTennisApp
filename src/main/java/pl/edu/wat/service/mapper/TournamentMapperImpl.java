package pl.edu.wat.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import pl.edu.wat.domain.Image;
import pl.edu.wat.domain.Tournament;
import pl.edu.wat.service.dto.TournamentDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class TournamentMapperImpl implements TournamentMapper {

    @Override
    public TournamentDTO tournamentToTournamentDTO(Tournament tournament) {
        if ( tournament == null ) {
            return null;
        }

        TournamentDTO tournamentDTO = new TournamentDTO();

        tournamentDTO.setImageId( tournamentImageId( tournament ) );
        tournamentDTO.setId( tournament.getId() );
        tournamentDTO.setName( tournament.getName() );

        return tournamentDTO;
    }

    @Override
    public List<TournamentDTO> tournamentsToTournamentDTOs(List<Tournament> tournaments) {
        if ( tournaments == null ) {
            return null;
        }

        List<TournamentDTO> list = new ArrayList<TournamentDTO>();
        for ( Tournament tournament : tournaments ) {
            list.add( tournamentToTournamentDTO( tournament ) );
        }

        return list;
    }

    @Override
    public Tournament tournamentDTOToTournament(TournamentDTO tournamentDTO) {
        if ( tournamentDTO == null ) {
            return null;
        }

        Tournament tournament = new Tournament();

        tournament.setImage( imageFromId( tournamentDTO.getImageId() ) );
        tournament.setId( tournamentDTO.getId() );
        tournament.setName( tournamentDTO.getName() );

        return tournament;
    }

    @Override
    public List<Tournament> tournamentDTOsToTournaments(List<TournamentDTO> tournamentDTOs) {
        if ( tournamentDTOs == null ) {
            return null;
        }

        List<Tournament> list = new ArrayList<Tournament>();
        for ( TournamentDTO tournamentDTO : tournamentDTOs ) {
            list.add( tournamentDTOToTournament( tournamentDTO ) );
        }

        return list;
    }

    private Long tournamentImageId(Tournament tournament) {

        if ( tournament == null ) {
            return null;
        }
        Image image = tournament.getImage();
        if ( image == null ) {
            return null;
        }
        Long id = image.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
