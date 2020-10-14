package com.example.exceptions;

public class PatientAlreadyExistsException extends Exception {

    public PatientAlreadyExistsException() {
        super();
    }

    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}
