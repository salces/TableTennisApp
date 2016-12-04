package pl.edu.wat.service.mapper;

import pl.edu.wat.domain.*;
import pl.edu.wat.service.dto.PlayerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Player and its DTO PlayerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface PlayerMapper {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.login", target = "managerLogin")
    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.prefix", target = "clubPrefix")
    PlayerDTO playerToPlayerDTO(Player player);

    List<PlayerDTO> playersToPlayerDTOs(List<Player> players);

    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "clubId", target = "club")
    Player playerDTOToPlayer(PlayerDTO playerDTO);

    List<Player> playerDTOsToPlayers(List<PlayerDTO> playerDTOs);

    default Club clubFromId(Long id) {
        if (id == null) {
            return null;
        }
        Club club = new Club();
        club.setId(id);
        return club;
    }
}
