package com.kings.okdhvi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullResourceException extends RuntimeException {
    public NullResourceException(String message) {
        super(message);
    }
}
