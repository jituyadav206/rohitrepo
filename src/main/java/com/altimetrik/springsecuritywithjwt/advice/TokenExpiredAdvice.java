package com.altimetrik.springsecuritywithjwt.advice;

import com.altimetrik.springsecuritywithjwt.exception.TokenExpiredMessageException;
import com.altimetrik.springsecuritywithjwt.model.TokenExpirationDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Component
public class TokenExpiredAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenExpiredMessageException.class)
    public final ResponseEntity<TokenExpiredMessageException> handleTokenExpiredException(TokenExpiredMessageException e, WebRequest request){
        TokenExpirationDetail dto = new TokenExpirationDetail("Token expired","Token is expired");
        return  new ResponseEntity(dto, HttpStatus.BAD_GATEWAY);

    }
}
