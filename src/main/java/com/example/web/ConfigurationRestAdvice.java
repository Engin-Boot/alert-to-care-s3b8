package com.example.web;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.entities.ErrorMessage;
import com.example.utility.Utility;
import com.example.vitalactions.VitalResolver;

@RestControllerAdvice
public class ConfigurationRestAdvice {
	
	final Logger logger = LogManager.getLogger(VitalResolver.class);
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleClientAlreadyExistsFoundException(Exception ex){
        logger.error("",ex);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    	logger.error("",ex);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), "Validation failed for request body: "+ Utility.getAllErrorMessages(ex), LocalDateTime.now().toString());
    }

}