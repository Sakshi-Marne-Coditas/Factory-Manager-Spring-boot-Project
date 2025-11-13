package com.FactoryManager.exceptionHandling;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
