package TheRealTubeProject.TheRealTube.controllers;

import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {

        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PatchMapping(path = "/{userId}", headers = ("content-type=application/json;charset=UTF-8"))
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {

        return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.OK);
    }

    @PostMapping(path = "/avatar/{userId}", headers = ("content-type=multipart/*"))
    public ResponseEntity<User> changeUsersAvatar(@RequestPart(value = "file") MultipartFile file,
                                                  @PathVariable("userId") Long userId) {
        userService.changeUsersAvatar(file, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
