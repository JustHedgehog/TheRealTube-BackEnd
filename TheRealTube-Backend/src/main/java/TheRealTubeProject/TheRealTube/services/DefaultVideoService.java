package TheRealTubeProject.TheRealTube.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultVideoService implements VideoService {


    private final ObjectStorageService objectStorageService;

    public DefaultVideoService(ObjectStorageService objectStorageService) {
        this.objectStorageService = objectStorageService;
    }

    @Override
    public String uploadVideo(MultipartFile file) {

       return objectStorageService.uploadToObjectStorage(file);
    }
}
