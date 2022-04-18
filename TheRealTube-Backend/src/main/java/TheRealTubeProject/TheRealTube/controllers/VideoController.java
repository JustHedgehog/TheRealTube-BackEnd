package TheRealTubeProject.TheRealTube.controllers;

import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/video/")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping(value = "userIdPlaceholder",headers=("content-type=multipart/*"))
    ResponseEntity<String> addVideo(@RequestPart(value = "file") MultipartFile file){
        videoService.uploadVideo(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("{videoId}")


    ResponseEntity<Void> deleteVideo(@PathVariable("videoId") Long videoId){

        //videoService.deleteVideo(videoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<Video>> getAllVideos(){

        List<Video> videoList = videoService.getAllVideos();

        return new ResponseEntity<>(videoList ,HttpStatus.OK);
    }

    @GetMapping("{videoId}")
    ResponseEntity<Video> getVideo(@PathVariable("videoId") Long videoId){

        Video video = videoService.getVideo(videoId);

        return new ResponseEntity<>(video ,HttpStatus.OK);
    }

    @GetMapping("userIdPlaceholder/video")
    ResponseEntity<String> getAllVideosRelatedToUser(@PathVariable("videoId") Long videoId){

        //  List<Video> videoList = videoService.getVideosRelatedToUser();

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
