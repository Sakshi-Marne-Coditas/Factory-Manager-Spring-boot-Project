package com.FactoryManager.exceptionHandling;

public class FactoryAlreadyExist extends RuntimeException{
    public FactoryAlreadyExist(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
