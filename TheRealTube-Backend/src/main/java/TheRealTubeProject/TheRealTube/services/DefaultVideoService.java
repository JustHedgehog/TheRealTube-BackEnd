package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DefaultVideoService implements VideoService {


    private final ObjectStorageService objectStorageService;
    private final VideoRepository videoRepository;

    public DefaultVideoService(ObjectStorageService objectStorageService,
                               VideoRepository videoRepository) {
        this.objectStorageService = objectStorageService;
        this.videoRepository = videoRepository;
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        Video newVideo = new Video();
        newVideo.setName(file.getOriginalFilename());
        videoRepository.save(newVideo);
       return objectStorageService.uploadToObjectStorage(file);
    }

    @Override
    public Video getVideo(Long videoId) {
        return videoRepository.getById(videoId);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
