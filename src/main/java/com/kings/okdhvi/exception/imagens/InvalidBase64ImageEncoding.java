package com.kings.okdhvi.exception.imagens;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidBase64ImageEncoding extends RuntimeException{
    public InvalidBase64ImageEncoding(String message) {
        super(message);
    }
}