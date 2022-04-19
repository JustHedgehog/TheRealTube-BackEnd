package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import org.apache.commons.io.FilenameUtils;
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
        String objectKey = objectStorageService.uploadToObjectStorage(file);
        newVideo.setFileurl(objectStorageService.getFileUrl(objectKey));
        newVideo.setObjectKey(objectKey);
        videoRepository.save(newVideo);
       return objectKey;
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
    public void deleteVideo(Long videoId) {
        objectStorageService.deleteVideo(videoId);
    }
}
