package h4rar.jwt.token.demo.exception.handler;

import h4rar.jwt.token.demo.dto.SimpleResponse;
import h4rar.jwt.token.demo.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class BadRequestHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public SimpleResponse BadRequestHandler(BadRequestException ex) {
        return new SimpleResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}

