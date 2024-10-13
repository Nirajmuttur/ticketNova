package com.ticketnova.bidService.exception.handler;


public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
