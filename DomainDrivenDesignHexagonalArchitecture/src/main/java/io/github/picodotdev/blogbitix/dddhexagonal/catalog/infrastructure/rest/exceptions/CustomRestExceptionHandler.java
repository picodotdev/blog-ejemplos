package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = String.format("Invalid value %s for %s", ex.getValue(), getPropertyNameFromValue(ex, request).orElse("property"));
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        Error error = new Error(HttpStatus.BAD_REQUEST, message, errors);
        return handleExceptionInternal(ex, error, headers, error.getStatus(), request);
    }

    private Optional<String> getPropertyNameFromValue(TypeMismatchException ex, WebRequest request) {
        String propertyName = ex.getPropertyName();
        if (propertyName == null) {
            Optional<String> value = request.getParameterMap().entrySet().stream().filter(e -> request.getParameter(e.getKey()).equals(ex.getValue())).map(e -> e.getKey()).findFirst();
            propertyName = value.orElse(null);
        }
        return Optional.ofNullable(propertyName);
    }
}