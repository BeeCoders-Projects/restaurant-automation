package com.beecoders.ras.exception.handler;

import com.beecoders.ras.exception.dish.DishNotFoundException;
import com.beecoders.ras.exception.s3.EmptyImageException;
import com.beecoders.ras.exception.s3.IncorrectImageFormatException;
import com.beecoders.ras.exception.s3.UploadImageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, status);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({EmptyImageException.class,
            IncorrectImageFormatException.class,
            UploadImageException.class})
    public ResponseEntity<Object> handlerImageUploadingException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(DishNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

}
