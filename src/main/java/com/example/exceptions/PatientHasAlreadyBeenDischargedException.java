package com.example.exceptions;

public class PatientHasAlreadyBeenDischargedException extends Exception {

    public PatientHasAlreadyBeenDischargedException() {
        super();
    }

    public PatientHasAlreadyBeenDischargedException(String message) {
        super(message);
    }
}
