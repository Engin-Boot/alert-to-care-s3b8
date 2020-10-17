package com.example.web;

import java.time.LocalDateTime;

import com.example.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.entities.ErrorMessage;
import com.example.utility.Utility;

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

    @ExceptionHandler(BedDoesNotBelongToSpecifiedClientException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBedDoesNotBelongToSpecifiedClientException(BedDoesNotBelongToSpecifiedClientException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(PatientDoesNotBelongToSpecifiedClientException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientDoesNotBelongToSpecifiedClientException(PatientDoesNotBelongToSpecifiedClientException ex){
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

    @ExceptionHandler(PatientCreatedWithIncorrectStatusWhenAdmittedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientCreatedWithIncorrectStatusWhenAdmittedException(PatientCreatedWithIncorrectStatusWhenAdmittedException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(BedHasAlreadyBeenOccupiedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBedHasAlreadyBeenOccupiedException(BedHasAlreadyBeenOccupiedException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(PatientHasNotSubscribedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePatientHasNotSubscribedException(PatientHasNotSubscribedException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(DeviceDoesNotExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDeviceDoesNotExistException(DeviceDoesNotExistException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(DeviceNotAssociatedWithBedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDeviceNotAssociatedWithBedException(DeviceNotAssociatedWithBedException ex){
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), "Validation failed for request body: "+ Utility.getAllErrorMessages(ex), LocalDateTime.now().toString());
    }

}