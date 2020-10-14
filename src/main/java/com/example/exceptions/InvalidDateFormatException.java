package com.example.exceptions;

public class InvalidDateFormatException extends Exception {

    public InvalidDateFormatException() {
        super();
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
