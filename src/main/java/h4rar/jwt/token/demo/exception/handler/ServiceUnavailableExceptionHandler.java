package h4rar.jwt.token.demo.exception.handler;

import h4rar.jwt.token.demo.dto.SimpleResponse;
import h4rar.jwt.token.demo.exception.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ServiceUnavailableExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ServiceUnavailableException.class)
    public SimpleResponse ServiceUnavailableException(ServiceUnavailableException ex){
        return new SimpleResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}