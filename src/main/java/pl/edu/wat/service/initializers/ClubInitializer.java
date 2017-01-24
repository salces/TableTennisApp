package pl.edu.wat.service.initializers;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.wat.domain.Club;
import pl.edu.wat.domain.User;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.repository.PlayerRepository;
import pl.edu.wat.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;

@Service
@DependsOn("clubRepository")
@Profile("dev")
public class ClubInitializer {

    @Inject
    private ClubRepository clubRepository;

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        User manager = userRepository.findOneByLogin("admin").get();
        Arrays.asList(
            Club.builder()
                .prefix("Radomiak")
                .location("Radom")
                .estabilished(1910)
                .email("radomiak@radom.pl")
                .homePage("http://radomiak.pl")
                .manager(manager)
                .build(),
            Club.builder()
                .prefix("Stal")
                .location("Rzeszow")
                .estabilished(1944)
                .email("stal@rzeszow.pl")
                .homePage("http://stalrzeszow.pl")
                .manager(manager)
                .build(),
            Club.builder()
                .prefix("Legia")
                .location("Warszawa")
                .estabilished(1916)
                .email("legia@warszawa.pl")
                .homePage("http://legia.com")
                .manager(manager)
                .build(),
            Club.builder()
                .prefix("Lech")
                .location("Poznan")
                .estabilished(1922)
                .email("lech@poznan.pl")
                .homePage("http://lechpoznan.pl")
                .manager(manager)
                .build()
        ).forEach(c -> clubRepository.save(c));
    }
}
