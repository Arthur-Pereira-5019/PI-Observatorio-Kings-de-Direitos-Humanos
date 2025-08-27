package com.kings.okdhvi.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoginInfoException extends RuntimeException {
    public InvalidLoginInfoException(String message) {
        super(message);
    }
}
