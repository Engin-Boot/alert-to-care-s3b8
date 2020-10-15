package com.example.exceptions;

public class PatientCreatedWithIncorrectStatusWhenAdmittedException extends Exception {

    public PatientCreatedWithIncorrectStatusWhenAdmittedException() {
        super();
    }

    public PatientCreatedWithIncorrectStatusWhenAdmittedException(String message) {
        super(message);
    }
}
