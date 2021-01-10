package h4rar.jwt.token.demo.exception.handler;

import h4rar.jwt.token.demo.dto.SimpleResponse;
import h4rar.jwt.token.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class NotFoundExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public SimpleResponse NotFoundException(NotFoundException ex){
        return new SimpleResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
