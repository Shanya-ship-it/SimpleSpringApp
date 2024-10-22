package com.example.SimpleSpringApp.exception;

public class UpdateStateException extends  RuntimeException{
    public UpdateStateException(String message){
        super(message);
    }
}
