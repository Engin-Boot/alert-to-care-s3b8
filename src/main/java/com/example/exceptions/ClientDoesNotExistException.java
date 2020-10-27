package com.example.exceptions;

public class ClientDoesNotExistException extends Exception {

    public ClientDoesNotExistException() {
        super();
    }

    public ClientDoesNotExistException(String message) {
        super(message);
    }
}
