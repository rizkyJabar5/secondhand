package com.secondhand.ecommerce.controller.handlers;

import com.secondhand.ecommerce.exceptions.AppsException;
import com.secondhand.ecommerce.exceptions.DataViolationException;
import com.secondhand.ecommerce.exceptions.IllegalException;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseStatusExceptionHandler {

    @ExceptionHandler(IllegalException.class)
    public ResponseEntity<Object> illegalActionDataHandler(IllegalException illegalException) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", illegalException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundHandler(UsernameNotFoundException usernameNotFoundException) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", usernameNotFoundException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppsException.class)
    public ResponseEntity<Object> notFoundHandler(AppsException inputOutputFileException) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", inputOutputFileException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataViolationException.class)
    public ResponseEntity<Object> nonAccessibleHandler(DataViolationException notFoundException) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", notFoundException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
