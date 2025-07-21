package beer.catalog.api.infrastructure.web.controller;


import beer.catalog.api.domain.exceptions.BeerNotFoundException;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.exceptions.NoAccessAuthorizationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ManufacturerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoManufacturerFound(ManufacturerNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(BeerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoBeerFound(BeerNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDifferentIDInBody(ResponseStatusException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDifferentIDInBody(HttpMessageNotReadableException ex) {
        return Map.of("message", "JSON parse error: Cannot deserialize value");
    }
    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationInBody(java.lang.IllegalArgumentException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationInBody(DataIntegrityViolationException ex) {
        return Map.of("message", "Referential integrity constraint violation. First you need to delete the Beers associated with this Manufacturer");
    }


    @ExceptionHandler(NoAccessAuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAuthorizationValidation(NoAccessAuthorizationException ex) {
        return Map.of("message", "Your user doesn't have permissions to perform this operation");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return Map.of(
                "message", "Validation errors",
                "errors", errors
        );
    }

}
