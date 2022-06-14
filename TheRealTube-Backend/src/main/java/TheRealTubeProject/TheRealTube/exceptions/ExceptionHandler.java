package TheRealTubeProject.TheRealTube.exceptions;

import TheRealTubeProject.TheRealTube.models.FailResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AppException.class)
    protected ResponseEntity<Object> customEntityException(AppException ex) {
        FailResponse error = new FailResponse();
        error.setMessage(ex.getMessage());
        if (
                ex instanceof UserNotFoundException ||
                ex instanceof VideoNotFoundException) {

            error.setStatusCode(404);
            return buildResponseEntity(error, HttpStatus.NOT_FOUND);

        }else if( ex instanceof TokenRefreshException || ex instanceof  NoPermissionException
        || ex instanceof BadCredentialsException){

            error.setStatusCode(401);
            return buildResponseEntity(error, HttpStatus.UNAUTHORIZED);
        }
        else {
            error.setStatusCode(500);
            return buildResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Object> buildResponseEntity(FailResponse error, HttpStatus status) {
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        FailResponse error = new FailResponse();
                error.setMessage(ex.getMessage());
                error.setStatusCode(status.value());
        return buildResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        FailResponse error = new FailResponse();
                error.setMessage(ex.getMessage());
                error.setStatusCode(status.value());
        return buildResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException
                                                                          ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        FailResponse error = new FailResponse();
        error.setMessage(ex.getMessage());
        error.setStatusCode(status.value());
        return buildResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        FailResponse error = new FailResponse();
        error.setMessage(ex.getMessage());
        error.setStatusCode(status.value());
        return buildResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        FailResponse error = new FailResponse();
        error.setMessage(ex.getMessage());
        error.setStatusCode(status.value());

        return buildResponseEntity(error, status);
    }



}
