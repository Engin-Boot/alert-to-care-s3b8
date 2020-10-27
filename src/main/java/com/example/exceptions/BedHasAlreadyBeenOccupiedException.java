package com.example.exceptions;

public class BedHasAlreadyBeenOccupiedException extends Exception {

    public BedHasAlreadyBeenOccupiedException() {
        super();
    }

    public BedHasAlreadyBeenOccupiedException(String message) {
        super(message);
    }
}
