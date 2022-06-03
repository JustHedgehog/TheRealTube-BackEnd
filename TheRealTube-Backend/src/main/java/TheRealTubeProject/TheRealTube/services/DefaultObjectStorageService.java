package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.ServerErrorException;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import com.ibm.cloud.objectstorage.AmazonClientException;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectMetadata;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManager;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManagerBuilder;
import com.ibm.cloud.objectstorage.services.s3.transfer.Upload;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultObjectStorageService implements ObjectStorageService {

    private final AmazonS3 client;
    private final VideoRepository videoRepository;

    public DefaultObjectStorageService(AmazonS3 client, VideoRepository videoRepository) {
        this.client = client;
        this.videoRepository = videoRepository;
    }

    private static final Logger log = LogManager.getLogger(ObjectStorageService.class.getName());


    public String uploadToObjectStorage(MultipartFile file) {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
        String objectKey = createObjectKey(file.getName());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        objectKey = objectKey.concat("." + extension);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        try {

            Upload upload = transferManager.upload(
                    "data-dx",
                    objectKey,
                    file.getInputStream(),
                    objectMetadata

            );
            upload.waitForCompletion();
        } catch (AmazonClientException | InterruptedException | IOException e) {
            log.error(e.getMessage());
            throw new ServerErrorException(e.getMessage());
        }
        transferManager.shutdownNow(false);
        return objectKey;
    }

    public String createObjectKey(String fileName) {
        UUID uuid = UUID.randomUUID();
        return uuid + "-" + fileName
                .replace(" ", "_")
                .replace("-", "_")
                .replace("/", "_");

    }

    public URL getFileUrl(String objectKey) {

        return client.getUrl("data-dx", objectKey);

    }

    @Override
    public void deleteVideo(Long videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        video.ifPresent(video1 -> {
            if (client.doesObjectExist("data-dx", video1.getObjectKey())) {
                client.deleteObject("data-dx", video1.getObjectKey());
            }
        });
    }

    @Override
    public void deleteObject(String objectKey) {
        if (client.doesObjectExist("data-dx", objectKey)) {
            client.deleteObject("data-dx", objectKey);
        }
    }

}
