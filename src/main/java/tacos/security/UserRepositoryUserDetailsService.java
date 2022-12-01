package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tacos.User;
import tacos.data.jpa.UserRepository;


@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userRepo.findByUserName(userName);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + userName + "' not found");
    }
}
