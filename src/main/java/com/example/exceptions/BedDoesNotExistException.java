package com.example.exceptions;

public class BedDoesNotExistException extends Exception {

    public BedDoesNotExistException() {
        super();
    }

    public BedDoesNotExistException(String message) {
        super(message);
    }
}
