package io.chege.blog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        else {
            return userRepository.save(user);
        }
    }
}
