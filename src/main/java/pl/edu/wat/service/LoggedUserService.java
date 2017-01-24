package pl.edu.wat.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.edu.wat.domain.User;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.security.SecurityUtils;

import javax.inject.Inject;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoggedUserService {

    @Inject
    private UserRepository userRepository;

    public User getLoggedUser() {
        return userRepository
            .findOneByLogin( SecurityUtils.getCurrentUserLogin() )
            .get();
    }

}
