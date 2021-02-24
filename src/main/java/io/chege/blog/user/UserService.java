package io.chege.blog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        else {
            return userRepository.save(user);
        }
    }
     public User getUser(String userId){
         Optional<User> user = userRepository.findById(userId);
         if (user.isPresent()){
             return user.get();
         }else{
             throw new IllegalStateException("User not found");
         }
     }

     public User updateUser(String userId, User userDetails){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            if (userDetails.getName() != null) {
                user.get().setName(userDetails.getName());
            }
            if (userDetails.getEmail() != null) {
                Optional<User> emailUser = userRepository.getUserByEmailExcept(userDetails.getEmail(), userId);
                if (emailUser.isPresent()){
                    throw new IllegalStateException("Email already exists");
                }
                user.get().setEmail(userDetails.getEmail());
            }
            if (userDetails.getStatus() != null) {
                user.get().setStatus(userDetails.getStatus());
            }
            userRepository.save(user.get());
            return user.get();
        }else{
            throw new IllegalStateException("User not found");
        }
     }
}
