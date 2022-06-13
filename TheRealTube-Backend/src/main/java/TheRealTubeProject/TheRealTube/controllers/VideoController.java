package TheRealTubeProject.TheRealTube.controllers;

import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.payload.response.ReturnMessage;
import TheRealTubeProject.TheRealTube.payload.response.VideoLikesStats;
import TheRealTubeProject.TheRealTube.services.UserService;
import TheRealTubeProject.TheRealTube.services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;
    private final UserService userService;

    public VideoController(VideoService videoService, UserService userService) {
        this.videoService = videoService;
        this.userService = userService;
    }


    @PostMapping(path = "upload/{userId}", headers = ("content-type=multipart/*"))
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Video> addVideo(@RequestPart(value = "file") MultipartFile file,
                                          @RequestPart(value = "name") String name,
                                          @RequestPart(value="description") String description,
                                          @PathVariable("userId") Long userId) {
        Video video = videoService.uploadVideo(file, name, description, userId);

        return new ResponseEntity<>(
                video,
                HttpStatus.CREATED);
    }
    @PostMapping(path ="/likes/{videoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> LikeDislikeVideo(
            @RequestParam(value = "userId") String userId,
            @PathVariable("videoId") Long videoId,
            @RequestParam(value = "like") Boolean like) {

        videoService.likeDislikeVideo(videoId, Long.valueOf(userId),like);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/{videoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReturnMessage> deleteVideo(@PathVariable("videoId") Long videoId) {


        videoService.deleteVideo(videoId);

        return new ResponseEntity<>(
                new ReturnMessage(videoId, "video deleted"),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {

        List<Video> videoList = videoService.getAllVideos();

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

    @GetMapping("likes/{videoId}")
    public ResponseEntity<VideoLikesStats> getVideosLikes(@PathVariable("videoId") Long videoId) {


        return new ResponseEntity<>(
                videoService.getVideosLikesStats(videoId),
                HttpStatus.OK);
    }

    @GetMapping("name/{regexName}")
    public ResponseEntity<List<Video>> getAllVideosByName(@PathVariable("regexName") String regexName) {

        List<Video> videoList = videoService.getAllVideosByName(regexName);

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }


    @GetMapping("{videoId}")
    public ResponseEntity<Video> getVideo(@PathVariable("videoId") Long videoId) {

        Video video = videoService.getVideo(videoId);

        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("{userId}/video")
    public ResponseEntity<List<Video>> getAllVideosRelatedToUser(@PathVariable("userId") Long userId) {

        List<Video> videoList = videoService.getVideosRelatedToUser(userId);

        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

}
