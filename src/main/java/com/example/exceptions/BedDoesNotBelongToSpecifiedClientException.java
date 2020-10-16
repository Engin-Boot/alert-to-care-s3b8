package com.example.exceptions;

public class BedDoesNotBelongToSpecifiedClientException extends Exception {

    public BedDoesNotBelongToSpecifiedClientException() {
        super();
    }

    public BedDoesNotBelongToSpecifiedClientException(String message) {
        super(message);
    }
}
