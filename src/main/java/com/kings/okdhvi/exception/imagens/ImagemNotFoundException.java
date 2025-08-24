package com.kings.okdhvi.exception.imagens;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImagemNotFoundException extends RuntimeException {
    public ImagemNotFoundException(String message) {
        super(message);
    }
}
