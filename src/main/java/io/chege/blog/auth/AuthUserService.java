package io.chege.blog.auth;

import io.chege.blog.user.User;
import io.chege.blog.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (user.isPresent() && user.get().getStatus()){

            System.out.println(user.get().getAuthorities());
            Set<GrantedAuthority> authorities =
                    user.get().getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toSet());
            return new AuthUser(user.get().getEmail(),user.get().getPassword(), authorities, true, true, true, true);
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
