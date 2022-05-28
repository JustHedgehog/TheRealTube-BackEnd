package TheRealTubeProject.TheRealTube.exceptions;
public class VideoNotFoundException extends AppException {

    public VideoNotFoundException() {
        super(ExceptionMessages.VIDEO_NOT_FOUND.message);
    }
}
