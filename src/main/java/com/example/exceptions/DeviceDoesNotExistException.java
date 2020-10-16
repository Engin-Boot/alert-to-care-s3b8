package com.example.exceptions;

public class DeviceDoesNotExistException extends Exception {

    public DeviceDoesNotExistException() {
        super();
    }

    public DeviceDoesNotExistException(String message) {
        super(message);
    }
}
