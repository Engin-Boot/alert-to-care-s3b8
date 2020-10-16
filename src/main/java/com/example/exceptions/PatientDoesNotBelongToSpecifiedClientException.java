package com.example.exceptions;

public class PatientDoesNotBelongToSpecifiedClientException extends Exception {

    public PatientDoesNotBelongToSpecifiedClientException() {
        super();
    }

    public PatientDoesNotBelongToSpecifiedClientException(String message) {
        super(message);
    }
}
