package com.example.exceptions;

public class PatientHasNotSubscribedException extends Exception {

    public PatientHasNotSubscribedException() {
        super();
    }

    public PatientHasNotSubscribedException(String message) {
        super(message);
    }
}
