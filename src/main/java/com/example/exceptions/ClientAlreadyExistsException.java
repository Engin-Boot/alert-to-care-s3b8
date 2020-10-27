package com.example.exceptions;

public class ClientAlreadyExistsException extends Exception {

    public ClientAlreadyExistsException() {
        super();
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
