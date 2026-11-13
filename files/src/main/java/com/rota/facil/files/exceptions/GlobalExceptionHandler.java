package com.rota.facil.files.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> fileNotFoundException(HttpServletRequest request, FileNotFoundException ex) {
        return this.resolveExceptions(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> invalidFileException(HttpServletRequest request, InvalidFileException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    private ResponseEntity<Object> resolveExceptions(HttpStatus status, HttpServletRequest request, Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("path", request.getRequestURI());

        if(ex instanceof MethodArgumentNotValidException beanValidationEx) {
            Map<String, String> errors = new HashMap<>();
            beanValidationEx.getBindingResult().getFieldErrors().forEach(error
                    -> errors.put(error.getField(), error.getDefaultMessage()));

            body.put("message", errors);
        } else {
            body.put("message", ex.getMessage());
        }


        return new ResponseEntity<>(body, status);
    }
}
