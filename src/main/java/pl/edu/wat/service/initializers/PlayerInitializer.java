package pl.edu.wat.service.initializers;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.wat.domain.Player;
import pl.edu.wat.domain.User;
import pl.edu.wat.repository.PlayerRepository;
import pl.edu.wat.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;

@Service
@DependsOn("playerRepository")
@Profile("dev")
public class PlayerInitializer {

    @Inject
    PlayerRepository playerRepository;

    @Inject
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        User manager = userRepository.findOneByLogin("admin").get();
        Arrays.asList(
            Player.builder()
                .name("Leonardo")
                .surname("DaVinci")
                .nationality("Italy")
                .manager(manager)
                .height(178).build(),
            Player.builder()
                .name("Michael")
                .surname("Angelo")
                .nationality("Italy")
                .manager(manager)
                .height(189).build(),
            Player.builder()
                .name("Dante")
                .surname("Alighieri")
                .nationality("Italy")
                .manager(manager)
                .height(156).build(),
            Player.builder()
                .name("Leonardo")
                .surname("Fibonacci")
                .nationality("Italy")
                .manager(manager)
                .height(192).build(),
            Player.builder()
                .name("Galileo")
                .surname("Galilei")
                .nationality("Italy")
                .manager(manager)
                .height(201).build(),
            Player.builder()
                .name("Immanuel")
                .surname("Kant")
                .nationality("Germany")
                .manager(manager)
                .height(158).build(),
            Player.builder()
                .name("Stefan")
                .surname("Banach")
                .nationality("Poland")
                .manager(manager)
                .height(221).build(),
            Player.builder()
                .name("Stanislaw")
                .surname("Ulam")
                .nationality("Poland")
                .manager(manager)
                .height(192).build(),
            Player.builder()
                .name("Albert")
                .surname("Einstein")
                .nationality("Germany")
                .manager(manager)
                .height(201).build(),
            Player.builder()
                .name("Dimitri")
                .surname("Mendelejer")
                .nationality("Russia")
                .manager(manager)
                .height(201).build(),
            Player.builder()
                .name("Nikola")
                .surname("Tesla")
                .nationality("Serbia")
                .manager(manager)
                .height(201).build(),
            Player.builder()
                .name("Friedrich")
                .surname("Nietzsche")
                .nationality("Germany")
                .manager(manager)
                .height(175).build(),
            Player.builder()
                .name("Mikolaj")
                .surname("Kopernik")
                .nationality("Poland")
                .manager(manager)
                .height(184).build(),
            Player.builder()
                .name("Ignacy")
                .surname("Lukasiewicz")
                .nationality("Poland")
                .manager(manager)
                .height(201).build()
        ).forEach(playerRepository::save);
    }
}
