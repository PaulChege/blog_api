package io.chege.blog.auth;

import io.chege.blog.user.User;
import io.chege.blog.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public AuthUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(s);
        if (user.isPresent()){
            return new AuthUser(user.get().getEmail(),user.get().getPassword(), null, true, true, true, true);
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
