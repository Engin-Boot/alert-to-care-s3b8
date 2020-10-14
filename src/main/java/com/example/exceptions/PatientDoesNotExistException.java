package com.example.exceptions;

public class PatientDoesNotExistException extends Exception {

    public PatientDoesNotExistException() {
        super();
    }

    public PatientDoesNotExistException(String message) {
        super(message);
    }
}
