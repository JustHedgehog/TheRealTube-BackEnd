package TheRealTubeProject.TheRealTube.exceptions;

public enum ExceptionMessages {

   USER_NOT_FOUND("User not found"),
    VIDEO_NOT_FOUND("Video not found"),
    NO_AUTHORIZED_ACTION("You have no permission to do this"),
    BAD_CREDENTIALS("Bad credentials"),
    REFRESH_TOKEN_EXPIRED("You need to login in again ;)");

    public final String message;

    private ExceptionMessages(String message){
        this.message = message;
    }
}
