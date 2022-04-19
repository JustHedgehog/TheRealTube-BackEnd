package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    Video getVideo(Long videoId);

    List<Video> getAllVideos();

    void deleteVideo(Long videoId);
}
