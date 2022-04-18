package TheRealTubeProject.TheRealTube.services;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    String uploadVideo(MultipartFile file);
}
