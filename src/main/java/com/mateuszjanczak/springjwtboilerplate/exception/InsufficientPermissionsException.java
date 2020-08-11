package com.mateuszjanczak.springjwtboilerplate.exception;

public class InsufficientPermissionsException extends RuntimeException {

    public InsufficientPermissionsException() {

    }

    public InsufficientPermissionsException(String message) {
        super(message);
    }

}
