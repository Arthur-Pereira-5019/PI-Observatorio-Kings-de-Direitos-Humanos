package com.kings.okdhvi.exception.postagem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RevisaoPostagemException extends RuntimeException {
    public RevisaoPostagemException(String message) {
        super(message);
    }
}
