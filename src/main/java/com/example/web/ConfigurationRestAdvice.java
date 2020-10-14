package com.example.web;

import com.example.entities.ErrorMessage;
import com.example.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ConfigurationRestAdvice {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleClientAlreadyExistsFoundException(ClientAlreadyExistsException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientAlreadyExistsFoundException(PatientAlreadyExistsException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidDateFormatException(InvalidDateFormatException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(BedDoesNotExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBedDoesNotExistException(BedDoesNotExistException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(BedHasAlreadyBeenOccupiedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBedHasAlreadyBeenOccupiedException(BedHasAlreadyBeenOccupiedException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(ClientDoesNotExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleClientDoesNotExistException(ClientDoesNotExistException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(PatientDoesNotExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientDoesNotExistException(PatientDoesNotExistException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(PatientHasAlreadyBeenDischargedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientHasAlreadyBeenDischargedException(PatientHasAlreadyBeenDischargedException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

}