package io.chege.blog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "SELECT * FROM posts WHERE user_id = ?1 and status = true", nativeQuery = true)
    List<Post> getPostsByUserId(String userId);

    @Query(value = "SELECT * FROM posts WHERE id = ?1 AND user_id = ?2 and status = true", nativeQuery = true)
    Optional<Post> getPostByUserId(String postId, String userId);
}
