package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultVideoService implements VideoService {


    private final ObjectStorageService objectStorageService;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public DefaultVideoService(ObjectStorageService objectStorageService,
                               VideoRepository videoRepository,
                               UserRepository userRepository) {
        this.objectStorageService = objectStorageService;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Video uploadVideo(MultipartFile file, String name, Long userId) {

        Video newVideo = new Video();
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ServerErrorException("user does not exists");
        }
        newVideo.setUser(user.get());
        user.get().getVideos().add(newVideo);
        newVideo.setName(name);
        String objectKey = objectStorageService.uploadToObjectStorage(file);
        newVideo.setFileurl(objectStorageService.getFileUrl(objectKey));
        newVideo.setObjectKey(objectKey);
        videoRepository.save(newVideo);
        userRepository.save(user.get());
        return newVideo;
    }

    @Override
    public Video getVideo(Long videoId) {
        return videoRepository.getById(videoId);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> getAllVideosByName(String nameRegex) {
        return videoRepository.findVideosByNameReg(nameRegex);
    }

    @Override
    public void deleteVideo(Long videoId) {
        objectStorageService.deleteVideo(videoId);
        videoRepository.deleteById(videoId);
    }

    @Override
    public List<Video> getVideosRelatedToUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getVideos();
        } else return Collections.emptyList();
    }
}
