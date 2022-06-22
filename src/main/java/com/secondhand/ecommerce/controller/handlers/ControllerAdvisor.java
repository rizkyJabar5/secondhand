package com.secondhand.ecommerce.controller.handlers;

import com.secondhand.ecommerce.exceptions.DuplicateDataExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseStatusExceptionHandler {

    @ExceptionHandler(DuplicateDataExceptions.class)
    public ResponseEntity<Object> duplicateDataHandler(
            DuplicateDataExceptions duplicateDataExceptions, WebRequest webRequest) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", duplicateDataExceptions.getMessage());


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
