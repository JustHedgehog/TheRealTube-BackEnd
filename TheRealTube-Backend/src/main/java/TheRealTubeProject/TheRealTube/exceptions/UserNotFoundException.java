package TheRealTubeProject.TheRealTube.exceptions;

public class UserNotFoundException extends AppException {

    public UserNotFoundException() {
        super(ExceptionMessages.USER_NOT_FOUND.message);
    }
}
