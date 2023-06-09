package ru.kpfu.itis.khabibullin.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.repositories.UsersRepository;

import static ru.kpfu.itis.khabibullin.aspects.ExceptionHandler.handleException;

/**
 * @author Khabibullin Alisher
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UsersRepository usersRepository;
    @Override
    @Cacheable("account")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            UsernameNotFoundException e = new UsernameNotFoundException("User not found");
            handleException(e);
            throw e;
        }

        return new UserDetailsImpl(user);
    }


}
