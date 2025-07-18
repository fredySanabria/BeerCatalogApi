package beer.catalog.api.infrastructure.web.controller;


import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ManufacturerNotFoundException.class)
    public ResponseEntity<String> handleNoManufacturerFound(ManufacturerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
