package dice.avaloq.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {
        return error(exception.getBindingResult().getFieldErrors()
                         .stream()
                         .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                         .collect(Collectors.toList()));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                         .stream()
                         .map(constraintViolation -> constraintViolation.getPropertyPath() + ": " + constraintViolation
                                                                                                        .getMessage())
                         .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
}