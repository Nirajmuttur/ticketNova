package com.ticketnova.bidService.exception.handler;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final List<String> errors;
    private final Object data;

    // Primary constructor with message only
    public ApplicationException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    // Constructor with HttpStatus and message
    public ApplicationException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, Collections.singletonList(message), null);
    }

    // Constructor with HttpStatus, message, and data
    public ApplicationException(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus, message, Collections.singletonList(message), data);
    }

    // Constructor with HttpStatus, message, errors, and data
    public ApplicationException(HttpStatus httpStatus, String message, List<String> errors, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
        this.data = data;
    }
}
