package com.mateuszjanczak.springjwtboilerplate.web;

import com.mateuszjanczak.springjwtboilerplate.exception.ErrorMessage;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage handleAnyException(Exception ex) {
        String errorMessage = ex.getLocalizedMessage() == null ? ex.getLocalizedMessage() : ex.toString();
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleJwtException(JwtException ex){
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage handleAuthenticationException(AuthenticationException ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN, ex.getMessage());
    }

}
