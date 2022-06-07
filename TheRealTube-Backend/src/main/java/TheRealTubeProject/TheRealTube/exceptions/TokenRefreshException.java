package TheRealTubeProject.TheRealTube.exceptions;

public class TokenRefreshException extends AppException {
    public TokenRefreshException() {
        super(ExceptionMessages.REFRESH_TOKEN_EXPIRED.message);
    }
}