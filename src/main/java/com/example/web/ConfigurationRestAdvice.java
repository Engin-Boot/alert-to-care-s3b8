package com.example.web;

import com.example.entities.ErrorMessage;
import com.example.exceptions.ClientAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ConfigurationRestAdvice {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientNotFoundException(ClientAlreadyExistsException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }
}