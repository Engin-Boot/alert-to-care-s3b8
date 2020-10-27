package com.example.exceptions;

public class DeviceNotAssociatedWithBedException extends Exception {

    public DeviceNotAssociatedWithBedException() {
        super();
    }

    public DeviceNotAssociatedWithBedException(String message) {
        super(message);
    }
}
