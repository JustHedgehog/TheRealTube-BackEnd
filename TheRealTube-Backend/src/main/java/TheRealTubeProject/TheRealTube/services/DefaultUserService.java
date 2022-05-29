package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.UserNotFoundException;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    UserRepository userRepository;
    ObjectStorageService objectStorageService;

    public DefaultUserService(UserRepository userRepository, ObjectStorageService objectStorageService) {
        this.userRepository = userRepository;
        this.objectStorageService = objectStorageService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void changeUsersAvatar(MultipartFile file, Long id) {
        userRepository.findById(id).ifPresent(user -> {
            if(user.getAvatarUrl()!=null){
                objectStorageService.deleteObject(user.getAvatarObjectKey());
            }
        });
        String objectStorageKey = objectStorageService.uploadToObjectStorage(file);
        userRepository.findById(id).ifPresent(user ->{
            user.setAvatarObjectKey(objectStorageKey);
            user.setAvatarUrl(objectStorageService.getFileUrl(objectStorageKey));
            });
    }
}
