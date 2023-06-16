package com.beecoders.ras.exception.handler;

import com.beecoders.ras.exception.dish.DishNotFoundException;
import com.beecoders.ras.exception.order.IllegalPaymentException;
import com.beecoders.ras.exception.order.IllegalUsingPromocodeException;
import com.beecoders.ras.exception.restaurant_table.RestaurantTableNotFoundException;
import com.beecoders.ras.exception.restaurant_table.TableStatusNotFoundException;
import com.beecoders.ras.exception.s3.EmptyImageException;
import com.beecoders.ras.exception.s3.IncorrectImageFormatException;
import com.beecoders.ras.exception.s3.UploadImageException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getFieldErrors()
                .forEach(error -> {
                    int size = error.getField().split("\\.").length;
                    errors.put(error.getField().split("\\.")[size-1], error.getDefaultMessage());
                } );
        return new ResponseEntity<>(errors, status);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({EmptyImageException.class,
            IncorrectImageFormatException.class,
            UploadImageException.class,
            IllegalArgumentException.class,
            IllegalUsingPromocodeException.class})
    public ResponseEntity<Object> handlerBadRequests(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({DishNotFoundException.class,
            EntityNotFoundException.class,
            TableStatusNotFoundException.class,
            RestaurantTableNotFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handlerAccessDeniedException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), FORBIDDEN);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(IllegalPaymentException.class)
    public ResponseEntity<Object> handlerIllegalPaymentException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), CONFLICT);
    }

}