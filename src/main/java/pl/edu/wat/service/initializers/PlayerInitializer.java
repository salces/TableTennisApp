package pl.edu.wat.service.initializers;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import pl.edu.wat.domain.Player;
import pl.edu.wat.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;

@Service
@DependsOn("playerRepository")
public class PlayerInitializer {

    @Inject
    PlayerRepository playerRepository;

    @PostConstruct
    public void init() {
        Arrays.asList(
            Player.builder()
                .name("Leonardo")
                .surname("DaVinci")
                .nationality("Italy")
                .height(178).build(),
            Player.builder()
                .name("Michael")
                .surname("Angelo")
                .nationality("Italy")
                .height(189).build(),
            Player.builder()
                .name("Dante")
                .surname("Alighieri")
                .nationality("Italy")
                .height(156).build(),
            Player.builder()
                .name("Leonardo")
                .surname("Fibonacci")
                .nationality("Italy")
                .height(192).build(),
            Player.builder()
                .name("Galileo")
                .surname("Galilei")
                .nationality("Italy")
                .height(201).build()
        ).forEach(playerRepository::save);
    }
}
