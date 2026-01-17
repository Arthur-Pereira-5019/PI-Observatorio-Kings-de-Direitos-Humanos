package com.kings.okdhvi.exception.imagens;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocumentFormat extends RuntimeException {
    public InvalidDocumentFormat(String message) {
        super(message);
    }
}

