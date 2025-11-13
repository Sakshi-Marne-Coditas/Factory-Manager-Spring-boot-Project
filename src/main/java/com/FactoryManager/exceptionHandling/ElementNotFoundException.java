package com.FactoryManager.exceptionHandling;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String userAlreadyExists) {

        super(userAlreadyExists);
    }
}
