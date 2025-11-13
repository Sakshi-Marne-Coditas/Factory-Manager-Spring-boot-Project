package com.FactoryManager.exceptionHandling;

public class ElementAlreadyExistException extends RuntimeException{
    public ElementAlreadyExistException(String s) {
        super(s);
    }
}
