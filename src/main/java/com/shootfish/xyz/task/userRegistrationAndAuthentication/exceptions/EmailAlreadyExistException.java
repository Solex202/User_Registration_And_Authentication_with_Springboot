package com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(String message){
        super(message);
    }
}
