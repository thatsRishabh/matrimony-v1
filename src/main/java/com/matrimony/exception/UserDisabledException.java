package com.matrimony.exception;

public class UserDisabledException extends Exception {

    public UserDisabledException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;
}
