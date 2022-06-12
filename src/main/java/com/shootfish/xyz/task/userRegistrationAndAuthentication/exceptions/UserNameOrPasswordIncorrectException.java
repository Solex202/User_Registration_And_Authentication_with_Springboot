package com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions;

public class UserNameOrPasswordIncorrectException extends RuntimeException{
    public UserNameOrPasswordIncorrectException(String message){
        super(message);
    }
}
