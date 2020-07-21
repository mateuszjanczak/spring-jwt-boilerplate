package com.mateuszjanczak.springjwtboilerplate.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

    private int errorCode;
    private String error;
    private String errorMessage;

    public ErrorMessage(HttpStatus status, String message) {
        this.errorCode = status.value();
        this.error = status.name();
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toJson() {
        return "{ \"errorCode\": " + "\"" + errorCode+"\", \"error\": " + "\"" + error + "\", \"errorMessage\": " + "\"" + errorMessage.replace("\"", "'") + "\" }";
    }

}
