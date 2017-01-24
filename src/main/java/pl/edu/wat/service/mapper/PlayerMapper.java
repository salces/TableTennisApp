package pl.edu.wat.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.Image;
import pl.edu.wat.domain.Player;
import pl.edu.wat.service.dto.PlayerDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface PlayerMapper {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.login", target = "managerLogin")
    @Mapping(source = "club.id", target = "clubId")
    @Mapping(source = "club.prefix", target = "clubPrefix")
    @Mapping(source = "image.id", target = "imageId")
    @Mapping(source = "image.alias", target = "imageAlias")
    PlayerDTO playerToPlayerDTO(Player player);

    List<PlayerDTO> playersToPlayerDTOs(List<Player> players);

    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "clubId", target = "club")
    @Mapping(source = "imageId", target = "image")
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

    default Image imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
