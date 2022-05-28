package TheRealTubeProject.TheRealTube.controllers;

import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        List<User> videoList = userService.getAllUsers();

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping(path = "avatar/{userId}", headers = ("content-type=multipart/*"))
    public ResponseEntity<User> changeUsersAvatar(@RequestPart(value = "file") MultipartFile file,
                                                  @PathVariable("userId") Long userId) {

        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
}
