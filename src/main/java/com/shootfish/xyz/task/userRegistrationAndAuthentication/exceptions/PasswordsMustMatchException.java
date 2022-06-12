package com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions;

public class PasswordsMustMatchException extends RuntimeException {
    public PasswordsMustMatchException(String message) {

        super(message);
    }
}
