package io.chege.blog.user;

import io.chege.blog.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping(path = "/signup")
    public ResponseEntity signUpUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            if (createdUser == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.accepted().body(createdUser);
            }
        } catch (Exception e) {
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity.ok().body(userService.getUser(userId));
        } catch (Exception e) {
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId,
                                     @RequestBody User user
                                     ) {

        try {
            return ResponseEntity.ok().body(userService.updateUser(userId, user));
        }catch(Exception e) {
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }
}
