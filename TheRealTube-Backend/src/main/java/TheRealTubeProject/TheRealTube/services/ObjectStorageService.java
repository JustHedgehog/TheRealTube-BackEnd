package TheRealTubeProject.TheRealTube.services;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;


public interface ObjectStorageService {

    String uploadToObjectStorage(MultipartFile file);

    String createObjectKey(String fileName);

    URL getFileUrl(String objectKey);

    void deleteVideo(Long videoId);
}
