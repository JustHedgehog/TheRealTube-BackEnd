package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.UserNotFoundException;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import TheRealTubeProject.TheRealTube.security.services.RefreshTokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Ref;
import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final ObjectStorageService objectStorageService;
    private final CommentService commentService;
    private final VideoService videoService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public DefaultUserService(UserRepository userRepository,
                              ObjectStorageService objectStorageService,
                              AuthService authService,
                              CommentService commentService,
                              VideoService videoService,
                              RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.objectStorageService = objectStorageService;
        this.authService = authService;
        this.commentService = commentService;
        this.videoService = videoService;
        this.refreshTokenService = refreshTokenService;
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
        authService.isHeHasPermissions(id);

        userRepository.findById(id).ifPresent(user -> {
            if(user.getAvatarUrl()!=null){
                objectStorageService.deleteObject(user.getAvatarObjectKey());
            }
        });
        String objectStorageKey = objectStorageService.uploadToObjectStorage(file);
        userRepository.findById(id).ifPresent(user ->{
            user.setAvatarObjectKey(objectStorageKey);
            user.setAvatarUrl(objectStorageService.getFileUrl(objectStorageKey).toString());
            userRepository.save(user);
            });

    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            refreshTokenService.deleteByUserId(userId);
            userRepository.delete(user);
        });
    }

    @Override
    public User updateUser(Long id, User user) {
        authService.isHeHasPermissions(id);
        User actualUser = getUserById(id);
        actualUser.setAvatarUrl(user.getAvatarUrl());
        actualUser.setEmail(user.getEmail());
        actualUser.setAvatarObjectKey(user.getAvatarObjectKey());
        actualUser.setPassword(user.getPassword());
        actualUser.setRoles(user.getRoles());
        actualUser.setUsername(user.getUsername());
        userRepository.save(actualUser);
        return actualUser;
    }
}
