package com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
