package TheRealTubeProject.TheRealTube.exceptions;

public class BadCredentialsException extends AppException {

    public BadCredentialsException() {
        super(ExceptionMessages.BAD_CREDENTIALS.message);
    }
}
