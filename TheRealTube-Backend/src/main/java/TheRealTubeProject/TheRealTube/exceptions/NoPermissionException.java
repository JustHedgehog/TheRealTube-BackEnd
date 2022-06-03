
package TheRealTubeProject.TheRealTube.exceptions;
public class NoPermissionException extends AppException {

    public NoPermissionException() {
        super(ExceptionMessages.NO_AUTHORIZED_ACTION.message);
    }
}




