package com.kings.okdhvi.exception.postagem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostagemNotFoundException extends RuntimeException {
    public PostagemNotFoundException(String message) {
        super(message);
    }
}
