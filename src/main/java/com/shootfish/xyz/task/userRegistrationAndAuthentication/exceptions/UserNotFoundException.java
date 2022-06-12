package com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {

        super(message);
    }
}
