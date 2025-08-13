package com.kings.okdhvi.exception;

import java.util.Date;

public record ExceptionResponse(Date momento, String mensagem, String detalhes) {

}