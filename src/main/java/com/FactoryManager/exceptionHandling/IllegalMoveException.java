package com.FactoryManager.exceptionHandling;

public class IllegalMoveException extends RuntimeException{
    public IllegalMoveException(String s) {
        super(s);
    }
}
