package TheRealTubeProject.TheRealTube;

import TheRealTubeProject.TheRealTube.controllers.VideoController;
import TheRealTubeProject.TheRealTube.mockRepository.MockUserRepo;
import TheRealTubeProject.TheRealTube.mockRepository.MockVideoLikeRepository;
import TheRealTubeProject.TheRealTube.mockRepository.MockVideoRepo;
import TheRealTubeProject.TheRealTube.models.ERole;
import TheRealTubeProject.TheRealTube.models.Role;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.payload.response.ReturnMessage;
import TheRealTubeProject.TheRealTube.services.AuthService;
import TheRealTubeProject.TheRealTube.services.DefaultVideoService;
import TheRealTubeProject.TheRealTube.services.ObjectStorageService;
import TheRealTubeProject.TheRealTube.services.VideoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = VideoControllerTest.class)
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "TheRealTubeProject.TheRealTube")
@AutoConfigureMockMvc
public class VideoControllerTest {

    @Autowired
    MockVideoRepo mockVideoRepo;
    @Autowired
    MockUserRepo mockUserRepo;

    @Autowired
    MockVideoLikeRepository mockVideoLikeRepository;

    @Autowired
    AuthService authService;
    ObjectStorageService objectStorageServiceMock;
    VideoController videoController;
    VideoService videoService;

    @BeforeEach
    public void init() {

        objectStorageServiceMock = Mockito.mock(ObjectStorageService.class);

        videoService = new DefaultVideoService(
                objectStorageServiceMock,
                mockVideoRepo,
                mockUserRepo,
                mockVideoLikeRepository,
                authService);

        videoController = new VideoController(videoService);

    }

    @AfterEach
    public void clear() {
        mockUserRepo.clean();
        mockVideoRepo.clean();
    }


    @Test
    void add_video_return_created() {
        addUser(1L);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );
        ResponseEntity actual = videoController.addVideo(file, "real_video", "Testowy opis",1L);

        Video added = ((Video) actual.getBody());
        ResponseEntity expected = new ResponseEntity<>(
                added,
                HttpStatus.CREATED);

        Assertions.assertEquals(expected, actual);

    }
    @Test
    void like_video_return_Ok() {
        addUser(1L);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );
        ResponseEntity actual = videoController.addVideo(file, "real_video", "Testowy opis",1L);
        Video added = ((Video) actual.getBody());
        videoController.LikeDislikeVideo("1",added.getId(),true);

        ResponseEntity expected = new ResponseEntity<>(
                added,
                HttpStatus.CREATED);

        Assertions.assertEquals(expected, actual);

    }
    @Test
    void delete_video_by_id_and_return_ok() throws MalformedURLException {
        addUser(1L);

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );

        URL ur1 = new URL("http", "example.com", "/pages/page1.html");
        when(objectStorageServiceMock.uploadToObjectStorage(any())).thenReturn("objectKey");
        when(objectStorageServiceMock.getFileUrl(any())).thenReturn(ur1);

        videoController.addVideo(file, "real_video", "Testowe video",1L);

        ResponseEntity actual = videoController.deleteVideo(1L);

        ResponseEntity expected = new ResponseEntity<>(
                new ReturnMessage(1L, "video deleted"),
                HttpStatus.OK);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void get_all_videos_return_video_list_successfully() throws MalformedURLException {

        addUser(1L);
        addUser(2L);
        URL ur2 = new URL("http", "example.com", "/pages/page1.html");
        when(objectStorageServiceMock.uploadToObjectStorage(any())).thenReturn("objectKey");
        when(objectStorageServiceMock.getFileUrl(any())).thenReturn(ur2);

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );
        videoController.addVideo(file, "real_video", "Testowe video",1L);
        MockMultipartFile file2
                = new MockMultipartFile(
                "file2",
                "this_is_video_trust_me2.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure!".getBytes()
        );
        videoController.addVideo(file2, "real_video2","Testowe video" ,2L);

        List<Video> videoList = videoController.getAllVideos().getBody();

        ResponseEntity actual = videoController.getAllVideos();

        ResponseEntity expected = new ResponseEntity<>(videoList, HttpStatus.OK);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void get_all_videos_by_name_return_one_successfully() {
        addUser(1L);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );
        videoController.addVideo(file, "real_video", "Testowe wideo",1L);

        ResponseEntity actual = videoController.getAllVideosByName("this_is_video");
        List<Video> added = ((List<Video>) actual.getBody());

        ResponseEntity expected = new ResponseEntity<>(
                added,
                HttpStatus.OK);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void get_all_videos_related_to_one_user_return_sucessfully_one_video() throws MalformedURLException {

        addUser(1L);
        addUser(2L);
        URL ur2 = new URL("http", "example.com", "/pages/page1.html");
        when(objectStorageServiceMock.uploadToObjectStorage(any())).thenReturn("objectKey");
        when(objectStorageServiceMock.getFileUrl(any())).thenReturn(ur2);

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "this_is_video_trust_me.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure".getBytes()
        );
        videoController.addVideo(file, "real_video", "Testowe video",1L);

        MockMultipartFile file2
                = new MockMultipartFile(
                "file2",
                "this_is_video_trust_me2.avi",
                MediaType.TEXT_PLAIN_VALUE,
                "video file content for sure!".getBytes()
        );
        Video addedVideo = videoController.addVideo(file2, "real_video2", "Testowe video",2L).getBody();

        ResponseEntity actual = videoController.getAllVideosRelatedToUser(2L);

        ResponseEntity expected = new ResponseEntity<>(List.of(addedVideo), HttpStatus.OK);

        Assertions.assertEquals(expected, actual);

    }

    private User addUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("temp");
        user.setPassword("for");
        user.setEmail("test@test");
        user.setRoles(Set.of(new Role(ERole.ROLE_USER)));
        mockUserRepo.save(user);
        return user;
    }


}
