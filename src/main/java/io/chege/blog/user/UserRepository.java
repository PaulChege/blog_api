package io.chege.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("Select u FROM User u WHERE u.email = ?1")
    Optional<User> getUserByEmail(String email);

    @Query(value="Select id from users where email = ?1 and id <> ?2", nativeQuery = true)
    Optional<User> getUserByEmailExcept(String email, String userId);
}
