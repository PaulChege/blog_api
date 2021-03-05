package io.chege.blog.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.getUserByEmail("blogadmin@blog.com").isEmpty()) {
                userRepository.save(new User("Admin User", "blogadmin@blog.com", "password", true, "admin"));
            }
            if (userRepository.getUserByEmail("bloguser@blog.com").isEmpty()) {
                userRepository.save(new User("Admin User", "bloguser@blog.com", "password", true, "user"));
            }
        };
    }
}
