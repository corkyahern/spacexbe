package com.spacex.spacex.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${error.resource-not-found}")
    private String resourceNotFound;
    @Value("${error.internal-server-error}")
    private String internalServerError;
    private ErrorDetails handleError(String code, Exception exc, WebRequest req){
        return new ErrorDetails(
                LocalDateTime.now(),
                exc.getMessage(),
                req.getDescription(false),
                code
        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exc, WebRequest req){
        return new ResponseEntity<>(handleError(resourceNotFound, exc, req), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exc, WebRequest req){
        return new ResponseEntity<>(handleError(internalServerError, exc, req), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
