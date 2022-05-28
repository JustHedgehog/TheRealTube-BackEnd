package TheRealTubeProject.TheRealTube.exceptions;

public enum ExceptionMessages {

   USER_NOT_FOUND("User not found"),
    VIDEO_NOT_FOUND("Video not found");

    public final String message;

    private ExceptionMessages(String message){
        this.message = message;
    }
}
