package ru.azor.simple.web.form.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchClientException(ClientException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchServerException(ServerException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(e.getMessage(), e.getValidationErrors()), e.getHttpStatus());
    }
}
