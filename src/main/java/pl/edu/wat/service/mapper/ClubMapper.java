package pl.edu.wat.service.mapper;

import pl.edu.wat.domain.*;
import pl.edu.wat.service.dto.ClubDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Club and its DTO ClubDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ClubMapper {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.login", target = "managerLogin")
    @Mapping(source = "image.id", target = "imageId")
    @Mapping(source = "image.alias", target = "imageAlias")
    ClubDTO clubToClubDTO(Club club);

    List<ClubDTO> clubsToClubDTOs(List<Club> clubs);

    @Mapping(source = "managerId", target = "manager")
    @Mapping(target = "players", ignore = true)
    @Mapping(source = "imageId", target = "image")
    @Mapping(target = "leagues", ignore = true)
    Club clubDTOToClub(ClubDTO clubDTO);

    List<Club> clubDTOsToClubs(List<ClubDTO> clubDTOs);

    default Image imageFromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
