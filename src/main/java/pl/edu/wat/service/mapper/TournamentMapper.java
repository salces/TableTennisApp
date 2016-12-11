package pl.edu.wat.service.mapper;

import pl.edu.wat.domain.*;
import pl.edu.wat.service.dto.TournamentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tournament and its DTO TournamentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TournamentMapper {

    @Mapping(source = "image.id", target = "imageId")
    @Mapping(target = "phase", ignore = true)
    @Mapping(target = "chosenPlayers", ignore = true )
    TournamentDTO tournamentToTournamentDTO(Tournament tournament);

    List<TournamentDTO> tournamentsToTournamentDTOs(List<Tournament> tournaments);

    @Mapping(source = "imageId", target = "image")
    @Mapping(target = "stages", ignore = true)
    Tournament tournamentDTOToTournament(TournamentDTO tournamentDTO);

    List<Tournament> tournamentDTOsToTournaments(List<TournamentDTO> tournamentDTOs);

    default Image imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
