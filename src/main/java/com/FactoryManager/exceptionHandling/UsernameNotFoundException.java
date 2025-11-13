package com.FactoryManager.exceptionHandling;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String userAlreadyExists) {

        super(userAlreadyExists);
    }
}
