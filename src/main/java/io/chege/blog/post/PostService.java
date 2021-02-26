package io.chege.blog.post;

import io.chege.blog.user.User;
import io.chege.blog.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getPosts(String userId){
        userService.confirmUser(userId);
        return postRepository.getPostsByUserId(userId);
    }

    public Post getPost(String postId, String userId){
        userService.confirmUser(userId);
        return postRepository.getPostByUserId(postId, userId).get();
    }

    public Post createPost(Post post){
        String userId = post.getUserId();
        userService.confirmUser(userId);
        post.setUser(userService.findById(userId));
        return postRepository.save(post);
    }

    public Post updatePost(String postId, String userId, Post postDetails){
        userService.confirmUser(userId);
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent()){
            if (postDetails.getTitle() != null){
                post.get().setTitle(postDetails.getTitle());
            }
            if (postDetails.getContent() != null){
                post.get().setContent(postDetails.getContent());
            }
            if (postDetails.getStatus() != null){
                post.get().setStatus(postDetails.getStatus());
            }
            return postRepository.save(post.get());
        }else{
            throw new IllegalStateException("Post not found");
        }
    }

}
