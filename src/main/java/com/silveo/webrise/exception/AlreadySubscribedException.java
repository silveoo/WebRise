package com.silveo.webrise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String serviceName) {
        super("User already subscribed to " + serviceName);
    }
}