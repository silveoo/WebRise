package com.silveo.webrise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, Object id) {
        super(entity + " with id=" + id + " not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}