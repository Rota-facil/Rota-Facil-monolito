package com.rota.facil.journey.http.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> boardPointInActiveTripException(HttpServletRequest request, BoardPointInActiveTripException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> createTripException(HttpServletRequest request, CreateTripException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> deactivateTripException(HttpServletRequest request, DeactivateTripExceptions ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> institutionInActiveTripException(HttpServletRequest request, InstitutionInActiveTripException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> invalidTimeToInitTripException(HttpServletRequest request, InvalidTimeToInitTripException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> invalidTripGoingReturnException(HttpServletRequest request, InvalidTripGoingReturnException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> maxCapacityLimitedException(HttpServletRequest request, MaxCapacityLimitedException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> notAllowedJoinTripException(HttpServletRequest request, NotAllowedJoinTripException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> routeNotFoundException(HttpServletRequest request, RouteNotFoundException ex) {
        return this.resolveExceptions(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> tripAlreadyCancelledException(HttpServletRequest request, TripAlreadyCancelledException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> TripCannotBeReturnInit(HttpServletRequest request, TripCannotBeReturnInit ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> tripCannotBeStartedException(HttpServletRequest request, TripCannotBeStartedException ex) {
        return this.resolveExceptions(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> tripNotFoundException(HttpServletRequest request, TripNotFoundException ex) {
        return this.resolveExceptions(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> tripUserNotFoundExeption(HttpServletRequest request, TripUserNotFoundExeption ex) {
        return this.resolveExceptions(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> updateRouteException(HttpServletRequest request, UpdateRouteException ex) {
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
