package com.altimetrik.springsecuritywithjwt.exception;

public class TokenExpiredMessageException extends Exception {

    public TokenExpiredMessageException(String msg) {
        super(msg);
    }
}
