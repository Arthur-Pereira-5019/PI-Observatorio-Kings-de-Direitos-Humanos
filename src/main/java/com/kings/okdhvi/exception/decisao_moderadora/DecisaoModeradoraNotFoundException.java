package com.kings.okdhvi.exception.decisao_moderadora;

import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DecisaoModeradoraNotFoundException extends RuntimeException {
    public DecisaoModeradoraNotFoundException(String message) {
        super(message);
    }
}
