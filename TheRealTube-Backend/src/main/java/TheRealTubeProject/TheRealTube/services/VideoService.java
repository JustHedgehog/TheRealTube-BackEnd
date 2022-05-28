package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Video;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface VideoService {
    Video uploadVideo(MultipartFile file, String name,String description ,Long userId);

    Video getVideo(Long videoId);

    List<Video> getAllVideos();

    void deleteVideo(Long videoId);

    List<Video> getVideosRelatedToUser(Long userId);

    List<Video> getAllVideosByName(String nameRegex);

}
