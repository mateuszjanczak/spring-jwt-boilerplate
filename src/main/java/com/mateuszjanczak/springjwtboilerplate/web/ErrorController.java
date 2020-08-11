package com.mateuszjanczak.springjwtboilerplate.web;

import com.mateuszjanczak.springjwtboilerplate.dto.error.ErrorResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.error.ValidationErrorResponse;
import com.mateuszjanczak.springjwtboilerplate.exception.UsernameIsAlreadyTakenException;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleAnyException(Exception ex) {
        String errorMessage = ex.getLocalizedMessage() == null ? ex.getLocalizedMessage() : ex.toString();
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleJwtException(JwtException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> fieldsErrors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = "Object validation failed. Check fields errors.";
        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, fieldsErrors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "User not found: username = " + ex.getMessage());
    }

    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleUsernameNotFoundException(UsernameIsAlreadyTakenException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "Username is already taken: username = " + ex.getMessage());
    }
}
