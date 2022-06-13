package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    void changeUsersAvatar(MultipartFile file, Long id);
    void deleteUser(Long userId);
    User updateUser(Long id, User user);

}
