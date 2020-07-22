package com.altimetrik.springsecuritywithjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenExpiredMessageException extends Exception {

    public TokenExpiredMessageException(String msg) {
        super(msg);
    }
}
