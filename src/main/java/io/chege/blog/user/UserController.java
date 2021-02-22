package io.chege.blog.user;

import io.chege.blog.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="/signup")
    public ResponseEntity signUpUser(@RequestBody User user){
        try {
            User createdUser = userService.createUser(user);
            if (createdUser == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.accepted().body(createdUser);
            }
        }
        catch(Exception e) {
            CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
            return new ResponseEntity(customError, customError.getStatus());
        }
    }
}
