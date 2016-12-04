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
    ClubDTO clubToClubDTO(Club club);

    List<ClubDTO> clubsToClubDTOs(List<Club> clubs);

    @Mapping(source = "managerId", target = "manager")
    @Mapping(target = "players", ignore = true)
    Club clubDTOToClub(ClubDTO clubDTO);

    List<Club> clubDTOsToClubs(List<ClubDTO> clubDTOs);
}
