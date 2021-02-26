package io.chege.blog.post;

import io.chege.blog.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity getPosts(@RequestParam(name = "userId") String userId){
        try{
            return ResponseEntity.ok().body(postService.getPosts(userId));
        }catch(Exception e){
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }

    @GetMapping(path="{postId}")
    public ResponseEntity getPost(@PathVariable(name="postId") String postId,
                                  @RequestParam(name = "userId") String userId){
        try{
            return ResponseEntity.ok().body(postService.getPost(postId, userId));}
        catch (Exception e){
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody Post post){
        try{
            return ResponseEntity.ok().body(postService.createPost(post));
        }catch (Exception e){
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }

    @PatchMapping(path="{postId}")
    public ResponseEntity createPost(@PathVariable(name = "postId") String postId,
                                     @RequestParam(name = "userId") String userId, @RequestBody Post post){
        try{
            return ResponseEntity.ok().body(postService.updatePost(postId, userId, post));
        }catch (Exception e){
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }
}
