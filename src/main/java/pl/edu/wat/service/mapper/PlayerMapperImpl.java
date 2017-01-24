package pl.edu.wat.service.mapper;

import org.springframework.stereotype.Component;
import pl.edu.wat.domain.Player;
import pl.edu.wat.service.dto.PlayerDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public PlayerDTO playerToPlayerDTO(Player player) {
        PlayerDTO playerDTO = PlayerDTO.builder()
            .id(player.getId())
            .name(player.getName())
            .surname(player.getSurname())
            .nationality(player.getNationality())
            .height(player.getHeight())
            .build();

        if(player.getManager() != null){
            playerDTO.setManagerId(player.getManager().getId());
            playerDTO.setManagerLogin(player.getManager().getLogin());
        }

        if(player.getClub() != null){
            playerDTO.setClubId(player.getClub().getId());
            playerDTO.setClubPrefix(player.getClub().getPrefix());
        }

        if(player.getImage() != null){
            playerDTO.setImageAlias(player.getImage().getAlias());
            playerDTO.setImageId(player.getImage().getId());
        }

        return playerDTO;
    }

    @Override
    public List<PlayerDTO> playersToPlayerDTOs(List<Player> players) {
        return players.stream().map(this::playerToPlayerDTO).collect(Collectors.toList());
    }

    @Override
    public Player playerDTOToPlayer(PlayerDTO playerDTO) {
        return Player.builder()
            .id(playerDTO.getId())
            .name(playerDTO.getName())
            .surname(playerDTO.getSurname())
            .nationality(playerDTO.getNationality())
            .height(playerDTO.getHeight())
            .club(clubFromId(playerDTO.getClubId()))
            .image(imageFromId(playerDTO.getImageId()))
            .build();
    }

    @Override
    public List<Player> playerDTOsToPlayers(List<PlayerDTO> playerDTOs) {
        return playerDTOs.stream().map(this::playerDTOToPlayer).collect(Collectors.toList());
    }
}
