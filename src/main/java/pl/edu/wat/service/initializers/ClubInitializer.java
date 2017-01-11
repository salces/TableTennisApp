package pl.edu.wat.service.initializers;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import pl.edu.wat.domain.Club;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;

@Service
@DependsOn("clubRepository")
public class ClubInitializer {

    @Inject
    private ClubRepository clubRepository;

    @PostConstruct
    public void init(){
        Arrays.asList(
            Club.builder()
                .prefix("Radomiak")
                .location("Radom")
                .estabilished(1910)
                .email("radomiak@radom.pl")
                .homePage("http://radomiak.pl")
                .build(),
            Club.builder()
                .prefix("Stal")
                .location("Rzeszow")
                .estabilished(1944)
                .email("stal@rzeszow.pl")
                .homePage("http://stalrzeszow.pl")
                .build(),
            Club.builder()
                .prefix("Legia")
                .location("Warszawa")
                .estabilished(1916)
                .email("legia@warszawa.pl")
                .homePage("http://legia.com")
                .build(),
            Club.builder()
                .prefix("Lech")
                .location("Poznan")
                .estabilished(1922)
                .email("lech@poznan.pl")
                .homePage("http://lechpoznan.pl")
                .build()
        ).forEach(c -> clubRepository.save(c));
    }
}
