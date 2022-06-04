package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.NoPermissionException;
import TheRealTubeProject.TheRealTube.exceptions.UserNotFoundException;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.models.VideoLike;
import TheRealTubeProject.TheRealTube.payload.response.VideoLikesStats;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import TheRealTubeProject.TheRealTube.repositories.VideoLikeRepository;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import TheRealTubeProject.TheRealTube.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultVideoService implements VideoService {


    private final ObjectStorageService objectStorageService;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    private final VideoLikeRepository videoLikeRepository;

    public DefaultVideoService(ObjectStorageService objectStorageService,
                               VideoRepository videoRepository,
                               UserRepository userRepository,
                               VideoLikeRepository videoLikeRepository) {
        this.objectStorageService = objectStorageService;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoLikeRepository = videoLikeRepository;
    }

    @Override
    public Video uploadVideo(MultipartFile file, String name,String description ,Long userId) {

        Video newVideo = new Video();
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        newVideo.setUser(user.get());
        user.get().getVideos().add(newVideo);
        newVideo.setName(name);
        newVideo.setDescription(description);
        String objectKey = objectStorageService.uploadToObjectStorage(file);
        newVideo.setFileurl(objectStorageService.getFileUrl(objectKey).toString());
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
    public void likeDislikeVideo(Long videoId,Long userId,boolean like) {

        videoRepository.findById(videoId).ifPresent(video -> {

                if(video.getLikes().keySet().stream().noneMatch(userIdsFromSet -> userId.equals(userIdsFromSet))){
                    VideoLike newLike = new VideoLike();
                    newLike.setLiked(like);
                    video.getLikes().put(userId,newLike);
                    videoLikeRepository.save(newLike);
                }else {
                    VideoLike existingLike = video.getLikes().get(userId);
                    existingLike.setLiked(like);
                    videoLikeRepository.save(existingLike);

                }
                videoRepository.save(video);
        });
    }

    @Override
    public VideoLikesStats getVideosLikesStats(Long videoId) {
        Video video = videoRepository.getById(videoId);
        return new VideoLikesStats()
                .withLikes(
                        video.getLikes().values()
                                .stream()
                                .filter(videoLike -> videoLike.getLiked()).count())
                .withDislikes(
                        video.getLikes().values()
                                .stream()
                                .filter(videoLike -> !videoLike.getLiked()).count());
    }

    @Override
    public void deleteVideo(Long videoId) {

        videoRepository.findById(videoId).ifPresent(video -> {

            UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            userRepository.findById(video.getUser().getId()).ifPresent(user -> {
                if(!user.getUsername().equals(userDetails.getUsername()) && roles.contains("ROLE_USER")) {
                    throw new NoPermissionException();
                }
            });

            userRepository.findById(video.getUser().getId()).ifPresent(user -> {
                user.getVideos().remove(video);
                videoRepository.deleteById(videoId);
                objectStorageService.deleteVideo(videoId);
            });
        });
    }

    @Override
    public List<Video> getVideosRelatedToUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getVideos();
        } else return Collections.emptyList();
    }
}
