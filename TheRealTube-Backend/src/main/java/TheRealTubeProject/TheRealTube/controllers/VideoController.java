package TheRealTubeProject.TheRealTube.controllers;

import TheRealTubeProject.TheRealTube.payload.response.ReturnMessage;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping(value = "userIdPlaceholder", headers = ("content-type=multipart/*"))
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Video> addVideo(@RequestPart(value = "file") MultipartFile file) {
        Video video = videoService.uploadVideo(file);
        return new ResponseEntity<>(
                video,
                HttpStatus.CREATED);
    }

    @DeleteMapping("{videoId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<ReturnMessage> deleteVideo(@PathVariable("videoId") Long videoId) {

        videoService.deleteVideo(videoId);

        return new ResponseEntity<>(
                new ReturnMessage(videoId, "video deleted"),
                HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<Video>> getAllVideos() {

        List<Video> videoList = videoService.getAllVideos();

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

    @GetMapping("{videoId}")
    ResponseEntity<Video> getVideo(@PathVariable("videoId") Long videoId) {

        Video video = videoService.getVideo(videoId);

        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("userIdPlaceholder/video")
    ResponseEntity<List<Video>> getAllVideosRelatedToUser(@PathVariable("userId") Long userId) {

        List<Video> videoList = videoService.getVideosRelatedToUser(userId);

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

}
